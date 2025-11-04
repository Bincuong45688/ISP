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
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/vouchers")
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
        try {
            VoucherResponse voucher = voucherService.createVoucher(request);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Voucher created successfully");
            response.put("data", voucher);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Failed to create voucher: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
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
        try {
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
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Failed to get vouchers: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    /**
     * Get voucher by ID
     * GET /api/vouchers/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getVoucherById(@PathVariable Long id) {
        try {
            VoucherResponse voucher = voucherService.getVoucherById(id);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", voucher);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Voucher not found: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    /**
     * Get voucher by code
     * GET /api/vouchers/code/{code}
     */
    @GetMapping("/code/{code}")
    public ResponseEntity<Map<String, Object>> getVoucherByCode(@PathVariable String code) {
        try {
            VoucherResponse voucher = voucherService.getVoucherByCode(code);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", voucher);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Voucher not found: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
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
        try {
            VoucherResponse voucher = voucherService.updateVoucher(id, request);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Voucher updated successfully");
            response.put("data", voucher);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Failed to update voucher: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    /**
     * Delete voucher
     * DELETE /api/vouchers/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteVoucher(@PathVariable Long id) {
        try {
            voucherService.deleteVoucher(id);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Voucher deleted successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Failed to delete voucher: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    /**
     * Apply voucher to calculate discount (User)
     * POST /api/vouchers/apply
     */
    @PostMapping("/apply")
    public ResponseEntity<Map<String, Object>> applyVoucher(@Valid @RequestBody ApplyVoucherRequest request) {
        try {
            VoucherDiscountResponse discount = voucherService.applyVoucher(request);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", discount);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    /**
     * Confirm voucher usage after successful payment
     * POST /api/vouchers/confirm/{code}
     */
    @PostMapping("/confirm/{code}")
    public ResponseEntity<Map<String, Object>> confirmVoucherUsage(@PathVariable String code) {
        try {
            voucherService.confirmVoucherUsage(code);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Voucher usage confirmed");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Failed to confirm voucher usage: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    /**
     * Get all valid vouchers (active and within date range)
     * GET /api/vouchers/valid
     */
    @GetMapping("/valid")
    public ResponseEntity<Map<String, Object>> getValidVouchers() {
        try {
            List<VoucherResponse> vouchers = voucherService.getValidVouchers();

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", vouchers);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Failed to get valid vouchers: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    /**
     * Deactivate expired vouchers (Admin/Staff only)
     * POST /api/vouchers/deactivate-expired
     */
    @PostMapping("/deactivate-expired")
    public ResponseEntity<Map<String, Object>> deactivateExpiredVouchers() {
        try {
            voucherService.deactivateExpiredVouchers();

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Expired vouchers deactivated successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Failed to deactivate expired vouchers: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
