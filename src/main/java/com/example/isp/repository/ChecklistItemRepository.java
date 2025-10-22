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

    // Search theo tên
    @Query("""
        select ci from ChecklistItem ci
        where lower(ci.itemName) like lower(concat('%', :keyword, '%'))
        """)
    List<ChecklistItem> searchByName(@Param("keyword") String keyword);
}
