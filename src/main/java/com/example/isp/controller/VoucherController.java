package com.example.isp.controller;

import com.example.isp.dto.request.ApplyVoucherRequest;
import com.example.isp.dto.request.CreateVoucherRequest;
import com.example.isp.dto.request.UpdateVoucherRequest;
import com.example.isp.dto.response.VoucherDiscountResponse;
import com.example.isp.dto.response.VoucherResponse;
import com.example.isp.model.enums.DiscountType;
import com.example.isp.service.VoucherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/vouchers")
@Transactional(readOnly = false)
@RequiredArgsConstructor
public class VoucherController {

    private final VoucherService voucherService;

    /**
     * Create new voucher (Staff only)
     * POST /api/vouchers
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Map<String, Object>> createVoucher(@Valid @RequestBody CreateVoucherRequest request) {
        VoucherResponse voucher = voucherService.createVoucher(request);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Tạo voucher thành công");
        response.put("data", voucher);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Get all vouchers with filters and pagination
     * GET /api/vouchers?code=SALE&discountType=PERCENTAGE&isActive=true&page=0&size=10&sort=createdAt,desc
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getVouchers(
            @RequestParam(required = false) String code,
            @RequestParam(required = false) DiscountType discountType,
            @RequestParam(required = false) Boolean isActive,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "DESC") Sort.Direction direction
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        Page<VoucherResponse> voucherPage = voucherService.getVouchers(
                code, discountType, isActive, startDate, endDate, pageable
        );

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("data", voucherPage.getContent());
        response.put("currentPage", voucherPage.getNumber());
        response.put("totalItems", voucherPage.getTotalElements());
        response.put("totalPages", voucherPage.getTotalPages());
        response.put("pageSize", voucherPage.getSize());

        return ResponseEntity.ok(response);
    }

    /**
     * Get voucher by ID
     * GET /api/vouchers/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getVoucherById(@PathVariable Long id) {
        VoucherResponse voucher = voucherService.getVoucherById(id);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("data", voucher);
        return ResponseEntity.ok(response);
    }

    /**
     * Get voucher by code
     * GET /api/vouchers/code/{code}
     */
    @GetMapping("/code/{code}")
    public ResponseEntity<Map<String, Object>> getVoucherByCode(@PathVariable String code) {
        VoucherResponse voucher = voucherService.getVoucherByCode(code);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("data", voucher);
        return ResponseEntity.ok(response);
    }

    /**
     * Update voucher
     * PUT /api/vouchers/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateVoucher(
            @PathVariable Long id,
            @Valid @RequestBody UpdateVoucherRequest request
    ) {
        VoucherResponse voucher = voucherService.updateVoucher(id, request);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Cập nhật voucher thành công");
        response.put("data", voucher);
        return ResponseEntity.ok(response);
    }

    /**
     * Delete voucher
     * DELETE /api/vouchers/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteVoucher(@PathVariable Long id) {
        voucherService.deleteVoucher(id);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Xóa voucher thành công");
        return ResponseEntity.ok(response);
    }

    /**
     * Apply voucher to calculate discount (User)
     * POST /api/vouchers/apply
     */
    @PostMapping("/apply")
    public ResponseEntity<Map<String, Object>> applyVoucher(@Valid @RequestBody ApplyVoucherRequest request) {
        VoucherDiscountResponse discount = voucherService.applyVoucher(request);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("data", discount);
        return ResponseEntity.ok(response);
    }

    /**
     * Confirm voucher usage after successful payment
     * POST /api/vouchers/confirm/{code}
     */
    @PostMapping("/confirm/{code}")
    public ResponseEntity<Map<String, Object>> confirmVoucherUsage(@PathVariable String code) {
        voucherService.confirmVoucherUsage(code);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Xác nhận sử dụng voucher thành công");
        return ResponseEntity.ok(response);
    }

    /**
     * Get all valid vouchers (active and within date range)
     * GET /api/vouchers/valid
     */
    @GetMapping("/valid")
    public ResponseEntity<Map<String, Object>> getValidVouchers() {
        List<VoucherResponse> vouchers = voucherService.getValidVouchers();

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("data", vouchers);
        return ResponseEntity.ok(response);
    }

    /**
     * Deactivate expired vouchers (Admin/Staff only)
     * POST /api/vouchers/deactivate-expired
     */
    @PostMapping("/deactivate-expired")
    public ResponseEntity<Map<String, Object>> deactivateExpiredVouchers() {
        voucherService.deactivateExpiredVouchers();

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Vô hiệu hóa voucher hết hạn thành công");
        return ResponseEntity.ok(response);
    }
}
