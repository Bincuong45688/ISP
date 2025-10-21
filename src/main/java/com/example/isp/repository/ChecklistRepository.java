package com.example.isp.repository;

import com.example.isp.model.Checklist;
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
public interface ChecklistRepository extends JpaRepository<Checklist, Long>, JpaSpecificationExecutor<Checklist> {

    // Cho phép dùng Specification (lọc động)
    @Override
    Page<Checklist> findAll(Specification<Checklist> spec, Pageable pageable);

    // Search theo tên
    @Query("""
        select c from Checklist c
        where lower(c.itemName) like lower(concat('%', :keyword, '%'))
        """)
    List<Checklist> searchByName(@Param("keyword") String keyword);

    // Kiểm tra tên đã tồn tại
    boolean existsByItemName(String itemName);
}
