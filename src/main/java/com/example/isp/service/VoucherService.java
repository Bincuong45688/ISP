package com.example.isp.service;

import com.example.isp.dto.request.ApplyVoucherRequest;
import com.example.isp.dto.request.CreateVoucherRequest;
import com.example.isp.dto.request.UpdateVoucherRequest;
import com.example.isp.dto.response.VoucherDiscountResponse;
import com.example.isp.dto.response.VoucherResponse;
import com.example.isp.model.enums.DiscountType;
import com.example.isp.model.Manager;
import com.example.isp.model.Voucher;
import com.example.isp.repository.ManagerRepository;
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
@Transactional
public class VoucherService {

    private final VoucherRepository voucherRepository;
    private final ManagerRepository managerRepository;

    /**
     * Create new voucher (Manager only)
     */
    @Transactional
    public VoucherResponse createVoucher(CreateVoucherRequest request) {
        // Validate code uniqueness
        String upperCode = request.getCode().trim().toUpperCase();
        if (voucherRepository.existsByCode(upperCode)) {
            throw new IllegalArgumentException("Mã voucher đã tồn tại: " + upperCode);
        }

        // Validate dates
        if (request.getEndDate().isBefore(request.getStartDate())) {
            throw new IllegalArgumentException("Ngày kết thúc phải sau ngày bắt đầu");
        }

        if (request.getStartDate().isBefore(LocalDateTime.now().minusDays(1))) {
            throw new IllegalArgumentException("Ngày bắt đầu không được là quá khứ");
        }

        // Validate discount value
        if (request.getDiscountValue().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Giá trị giảm giá phải lớn hơn 0");
        }

        // Validate percentage discount
        if (request.getDiscountType() == DiscountType.PERCENTAGE) {
            if (request.getDiscountValue().compareTo(new BigDecimal("100")) > 0) {
                throw new IllegalArgumentException("Giảm giá theo phần trăm không được vượt quá 100%");
            }
            if (request.getDiscountValue().compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("Phần trăm giảm giá phải lớn hơn 0");
            }
        }

        // Validate min order amount
        if (request.getMinOrderAmount() != null && request.getMinOrderAmount().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Giá trị đơn hàng tối thiểu không được âm");
        }

        // Validate max discount amount for percentage type
        if (request.getDiscountType() == DiscountType.PERCENTAGE) {
            if (request.getMaxDiscountAmount() != null && request.getMaxDiscountAmount().compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("Giá trị giảm giá tối đa phải lớn hơn 0");
            }
        }

        // Validate usage limit
        if (request.getUsageLimit() != null && request.getUsageLimit() <= 0) {
            throw new IllegalArgumentException("Giới hạn sử dụng phải lớn hơn 0");
        }

        // Get manager if provided
        Manager manager = null;
        if (request.getCreatedBy() != null) {
            manager = managerRepository.findById(request.getCreatedBy())
                    .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy nhân viên với ID: " + request.getCreatedBy()));
        }

        // Create voucher
        Voucher voucher = Voucher.builder()
                .code(upperCode)
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
                .createdBy(manager)
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
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy voucher với ID: " + id));
        return convertToResponse(voucher);
    }

    /**
     * Get voucher by code
     */
    public VoucherResponse getVoucherByCode(String code) {
        Voucher voucher = voucherRepository.findByCode(code.trim().toUpperCase())
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy voucher với mã: " + code));
        return convertToResponse(voucher);
    }

    /**
     * Update voucher
     */
    @Transactional
    public VoucherResponse updateVoucher(Long id, UpdateVoucherRequest request) {
        Voucher voucher = voucherRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy voucher với ID: " + id));

        // Update fields if provided
        if (request.getDescription() != null) {
            voucher.setDescription(request.getDescription());
        }
        
        if (request.getDiscountType() != null) {
            voucher.setDiscountType(request.getDiscountType());
        }
        
        if (request.getDiscountValue() != null) {
            if (request.getDiscountValue().compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("Giá trị giảm giá phải lớn hơn 0");
            }
            
            DiscountType type = request.getDiscountType() != null ? request.getDiscountType() : voucher.getDiscountType();
            if (type == DiscountType.PERCENTAGE && request.getDiscountValue().compareTo(new BigDecimal("100")) > 0) {
                throw new IllegalArgumentException("Giảm giá theo phần trăm không được vượt quá 100%");
            }
            voucher.setDiscountValue(request.getDiscountValue());
        }
        
        if (request.getMinOrderAmount() != null) {
            if (request.getMinOrderAmount().compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException("Giá trị đơn hàng tối thiểu không được âm");
            }
            voucher.setMinOrderAmount(request.getMinOrderAmount());
        }
        
        if (request.getMaxDiscountAmount() != null) {
            if (request.getMaxDiscountAmount().compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("Giá trị giảm giá tối đa phải lớn hơn 0");
            }
            voucher.setMaxDiscountAmount(request.getMaxDiscountAmount());
        }
        
        if (request.getUsageLimit() != null) {
            if (request.getUsageLimit() <= 0) {
                throw new IllegalArgumentException("Giới hạn sử dụng phải lớn hơn 0");
            }
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
            throw new IllegalArgumentException("Ngày kết thúc phải sau ngày bắt đầu");
        }

        voucher = voucherRepository.save(voucher);
        return convertToResponse(voucher);
    }

    /**
     * Delete voucher
     */
    @Transactional
    public void deleteVoucher(Long id) {
        Voucher voucher = voucherRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy voucher với ID: " + id));
        
        // Check if voucher has been used
        if (voucher.getUsedCount() > 0) {
            throw new IllegalStateException("Không thể xóa voucher đã được sử dụng. Hãy vô hiệu hóa thay vì xóa.");
        }
        
        voucherRepository.deleteById(id);
    }

    /**
     * Apply voucher to order and calculate discount
     */
    public VoucherDiscountResponse applyVoucher(ApplyVoucherRequest request) {
        Voucher voucher = voucherRepository.findByCode(request.getVoucherCode().trim().toUpperCase())
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy voucher: " + request.getVoucherCode()));

        // Validate voucher
        if (!voucher.isValid()) {
            String reason = !voucher.getIsActive() ? "Voucher đã bị vô hiệu hóa" :
                    LocalDateTime.now().isBefore(voucher.getStartDate()) ? "Voucher chưa có hiệu lực" :
                            LocalDateTime.now().isAfter(voucher.getEndDate()) ? "Voucher đã hết hạn" :
                                    "Voucher đã hết lượt sử dụng";
            throw new IllegalStateException(reason);
        }

        // Check minimum order amount
        if (!voucher.canBeUsedForAmount(request.getOrderAmount())) {
            throw new IllegalArgumentException(
                    String.format("Giá trị đơn hàng tối thiểu là %s VNĐ", voucher.getMinOrderAmount())
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
                .message("Áp dụng voucher thành công")
                .build();
    }

    /**
     * Confirm voucher usage (increment used count)
     */
    @Transactional
    public void confirmVoucherUsage(String code) {
        Voucher voucher = voucherRepository.findByCode(code.trim().toUpperCase())
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy voucher: " + code));

        if (!voucher.isValid()) {
            throw new IllegalStateException("Voucher không hợp lệ");
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
                .createdBy(voucher.getCreatedBy() != null ? voucher.getCreatedBy().getManagerId() : null)
                .createdByName(voucher.getCreatedBy() != null ? voucher.getCreatedBy().getManagerName() : null)
                .build();
    }
}
