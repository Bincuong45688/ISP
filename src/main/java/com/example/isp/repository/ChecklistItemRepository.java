package com.example.isp.repository;

import com.example.isp.model.ChecklistItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChecklistItemRepository extends JpaRepository<ChecklistItem, Long>, JpaSpecificationExecutor<ChecklistItem> {

    // Cho phép dùng Specification (lọc theo tên)
    @Override
    Page<ChecklistItem> findAll(Specification<ChecklistItem> spec, Pageable pageable);

    // Search theo tên (chỉ lấy active items)
    @Query("""
        select ci from ChecklistItem ci
        where lower(ci.itemName) like lower(concat('%', :keyword, '%'))
        and ci.isActive = true
        """)
    List<ChecklistItem> searchByName(@Param("keyword") String keyword);

    // Lấy tất cả items active
    @Query("SELECT ci FROM ChecklistItem ci WHERE ci.isActive = true")
    List<ChecklistItem> findAllActive();

    // Tìm theo ID và active
    @Query("SELECT ci FROM ChecklistItem ci WHERE ci.itemId = :id AND ci.isActive = true")
    java.util.Optional<ChecklistItem> findByIdAndActive(@Param("id") Long id);
}
