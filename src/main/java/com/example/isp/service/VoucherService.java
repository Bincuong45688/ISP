package com.example.isp.service;

import com.example.isp.dto.request.ApplyVoucherRequest;
import com.example.isp.dto.request.CreateVoucherRequest;
import com.example.isp.dto.request.UpdateVoucherRequest;
import com.example.isp.dto.response.VoucherDiscountResponse;
import com.example.isp.dto.response.VoucherResponse;
import com.example.isp.model.enums.DiscountType;
import com.example.isp.model.Staff;
import com.example.isp.model.Voucher;
import com.example.isp.repository.StaffRepository;
import com.example.isp.repository.VoucherRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VoucherService {

    private final VoucherRepository voucherRepository;
    private final StaffRepository staffRepository;

    /**
     * Create new voucher (Staff only)
     */
    @Transactional
    public VoucherResponse createVoucher(CreateVoucherRequest request) {
        // Validate code uniqueness
        if (voucherRepository.existsByCode(request.getCode())) {
            throw new RuntimeException("Voucher code already exists: " + request.getCode());
        }

        // Validate dates
        if (request.getEndDate().isBefore(request.getStartDate())) {
            throw new RuntimeException("End date must be after start date");
        }

        // Validate percentage discount
        if (request.getDiscountType() == DiscountType.PERCENTAGE) {
            if (request.getDiscountValue().compareTo(new BigDecimal("100")) > 0) {
                throw new RuntimeException("Percentage discount cannot exceed 100%");
            }
        }

        // Get staff if provided
        Staff staff = null;
        if (request.getCreatedBy() != null) {
            staff = staffRepository.findById(request.getCreatedBy())
                    .orElseThrow(() -> new RuntimeException("Staff not found with id: " + request.getCreatedBy()));
        }

        // Create voucher
        Voucher voucher = Voucher.builder()
                .code(request.getCode().toUpperCase())
                .description(request.getDescription())
                .discountType(request.getDiscountType())
                .discountValue(request.getDiscountValue())
                .minOrderAmount(request.getMinOrderAmount())
                .maxDiscountAmount(request.getMaxDiscountAmount())
                .usageLimit(request.getUsageLimit())
                .usedCount(0)
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .isActive(request.getIsActive() != null ? request.getIsActive() : true)
                .createdBy(staff)
                .build();

        voucher = voucherRepository.save(voucher);
        return convertToResponse(voucher);
    }

    /**
     * Get all vouchers with filters and pagination
     */
    public Page<VoucherResponse> getVouchers(
            String code,
            DiscountType discountType,
            Boolean isActive,
            LocalDateTime startDate,
            LocalDateTime endDate,
            Pageable pageable
    ) {
        Page<Voucher> vouchers = voucherRepository.findWithFilters(
                code, discountType, isActive, startDate, endDate, pageable
        );
        return vouchers.map(this::convertToResponse);
    }

    /**
     * Get voucher by ID
     */
    public VoucherResponse getVoucherById(Long id) {
        Voucher voucher = voucherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Voucher not found with id: " + id));
        return convertToResponse(voucher);
    }

    /**
     * Get voucher by code
     */
    public VoucherResponse getVoucherByCode(String code) {
        Voucher voucher = voucherRepository.findByCode(code.toUpperCase())
                .orElseThrow(() -> new RuntimeException("Voucher not found with code: " + code));
        return convertToResponse(voucher);
    }

    /**
     * Update voucher
     */
    @Transactional
    public VoucherResponse updateVoucher(Long id, UpdateVoucherRequest request) {
        Voucher voucher = voucherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Voucher not found with id: " + id));

        // Update fields if provided
        if (request.getDescription() != null) {
            voucher.setDescription(request.getDescription());
        }
        if (request.getDiscountType() != null) {
            voucher.setDiscountType(request.getDiscountType());
        }
        if (request.getDiscountValue() != null) {
            if (request.getDiscountType() == DiscountType.PERCENTAGE 
                && request.getDiscountValue().compareTo(new BigDecimal("100")) > 0) {
                throw new RuntimeException("Percentage discount cannot exceed 100%");
            }
            voucher.setDiscountValue(request.getDiscountValue());
        }
        if (request.getMinOrderAmount() != null) {
            voucher.setMinOrderAmount(request.getMinOrderAmount());
        }
        if (request.getMaxDiscountAmount() != null) {
            voucher.setMaxDiscountAmount(request.getMaxDiscountAmount());
        }
        if (request.getUsageLimit() != null) {
            voucher.setUsageLimit(request.getUsageLimit());
        }
        if (request.getStartDate() != null) {
            voucher.setStartDate(request.getStartDate());
        }
        if (request.getEndDate() != null) {
            voucher.setEndDate(request.getEndDate());
        }
        if (request.getIsActive() != null) {
            voucher.setIsActive(request.getIsActive());
        }

        // Validate dates
        if (voucher.getEndDate().isBefore(voucher.getStartDate())) {
            throw new RuntimeException("End date must be after start date");
        }

        voucher = voucherRepository.save(voucher);
        return convertToResponse(voucher);
    }

    /**
     * Delete voucher
     */
    @Transactional
    public void deleteVoucher(Long id) {
        if (!voucherRepository.existsById(id)) {
            throw new RuntimeException("Voucher not found with id: " + id);
        }
        voucherRepository.deleteById(id);
    }

    /**
     * Apply voucher to order and calculate discount
     */
    public VoucherDiscountResponse applyVoucher(ApplyVoucherRequest request) {
        Voucher voucher = voucherRepository.findByCode(request.getVoucherCode().toUpperCase())
                .orElseThrow(() -> new RuntimeException("Voucher not found: " + request.getVoucherCode()));

        // Validate voucher
        if (!voucher.isValid()) {
            String reason = !voucher.getIsActive() ? "Voucher is inactive" :
                           LocalDateTime.now().isBefore(voucher.getStartDate()) ? "Voucher not yet active" :
                           LocalDateTime.now().isAfter(voucher.getEndDate()) ? "Voucher has expired" :
                           "Voucher usage limit reached";
            throw new RuntimeException(reason);
        }

        // Check minimum order amount
        if (!voucher.canBeUsedForAmount(request.getOrderAmount())) {
            throw new RuntimeException(
                String.format("Minimum order amount is %s", voucher.getMinOrderAmount())
            );
        }

        // Calculate discount
        BigDecimal discountAmount = voucher.calculateDiscount(request.getOrderAmount());
        BigDecimal finalAmount = request.getOrderAmount().subtract(discountAmount);

        return VoucherDiscountResponse.builder()
                .voucherCode(voucher.getCode())
                .originalAmount(request.getOrderAmount())
                .discountAmount(discountAmount)
                .finalAmount(finalAmount)
                .message("Voucher applied successfully")
                .build();
    }

    /**
     * Confirm voucher usage (increment used count)
     */
    @Transactional
    public void confirmVoucherUsage(String code) {
        Voucher voucher = voucherRepository.findByCode(code.toUpperCase())
                .orElseThrow(() -> new RuntimeException("Voucher not found: " + code));

        if (!voucher.isValid()) {
            throw new RuntimeException("Voucher is not valid");
        }

        voucher.setUsedCount(voucher.getUsedCount() + 1);
        voucherRepository.save(voucher);
    }

    /**
     * Get all valid vouchers
     */
    public List<VoucherResponse> getValidVouchers() {
        List<Voucher> vouchers = voucherRepository.findValidVouchers(LocalDateTime.now());
        return vouchers.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Deactivate expired vouchers
     */
    @Transactional
    public void deactivateExpiredVouchers() {
        List<Voucher> expiredVouchers = voucherRepository.findExpiredVouchers(LocalDateTime.now());
        for (Voucher voucher : expiredVouchers) {
            if (voucher.getIsActive()) {
                voucher.setIsActive(false);
                voucherRepository.save(voucher);
            }
        }
    }

    // Helper method to convert entity to response
    private VoucherResponse convertToResponse(Voucher voucher) {
        return VoucherResponse.builder()
                .voucherId(voucher.getVoucherId())
                .code(voucher.getCode())
                .description(voucher.getDescription())
                .discountType(voucher.getDiscountType())
                .discountValue(voucher.getDiscountValue())
                .minOrderAmount(voucher.getMinOrderAmount())
                .maxDiscountAmount(voucher.getMaxDiscountAmount())
                .usageLimit(voucher.getUsageLimit())
                .usedCount(voucher.getUsedCount())
                .startDate(voucher.getStartDate())
                .endDate(voucher.getEndDate())
                .isActive(voucher.getIsActive())
                .isValid(voucher.isValid())
                .createdAt(voucher.getCreatedAt())
                .updatedAt(voucher.getUpdatedAt())
                .createdBy(voucher.getCreatedBy() != null ? voucher.getCreatedBy().getStaffId() : null)
                .createdByName(voucher.getCreatedBy() != null ? voucher.getCreatedBy().getStaffName() : null)
                .build();
    }
}
