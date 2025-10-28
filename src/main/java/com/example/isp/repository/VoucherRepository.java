package com.example.isp.repository;

import com.example.isp.model.enums.DiscountType;
import com.example.isp.model.Voucher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, Long> {

    // Find voucher by code
    Optional<Voucher> findByCode(String code);

    // Check if code exists
    boolean existsByCode(String code);

    // Find active vouchers
    Page<Voucher> findByIsActive(Boolean isActive, Pageable pageable);

    // Find vouchers by discount type
    Page<Voucher> findByDiscountType(DiscountType discountType, Pageable pageable);

    // Find valid vouchers (active and within date range)
    @Query("SELECT v FROM Voucher v WHERE v.isActive = true " +
           "AND v.startDate <= :now AND v.endDate >= :now " +
           "AND (v.usageLimit IS NULL OR v.usedCount < v.usageLimit)")
    List<Voucher> findValidVouchers(@Param("now") LocalDateTime now);

    // Find vouchers with filters and pagination
    @Query("SELECT v FROM Voucher v WHERE " +
           "(:code IS NULL OR LOWER(v.code) LIKE LOWER(CONCAT('%', :code, '%'))) " +
           "AND (:discountType IS NULL OR v.discountType = :discountType) " +
           "AND (:isActive IS NULL OR v.isActive = :isActive) " +
           "AND (:startDate IS NULL OR v.startDate >= :startDate) " +
           "AND (:endDate IS NULL OR v.endDate <= :endDate)")
    Page<Voucher> findWithFilters(
        @Param("code") String code,
        @Param("discountType") DiscountType discountType,
        @Param("isActive") Boolean isActive,
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate,
        Pageable pageable
    );

    // Find expired vouchers
    @Query("SELECT v FROM Voucher v WHERE v.endDate < :now")
    List<Voucher> findExpiredVouchers(@Param("now") LocalDateTime now);

    // Find vouchers created by staff
    Page<Voucher> findByCreatedBy_StaffId(Long staffId, Pageable pageable);
}
