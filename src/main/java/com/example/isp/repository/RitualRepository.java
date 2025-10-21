package com.example.isp.repository;

import com.example.isp.model.Ritual;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RitualRepository extends JpaRepository<Ritual, Long>, JpaSpecificationExecutor<Ritual> {

    // Cho phép dùng Specification (lọc theo tên, vùng miền)
    @Override
    @EntityGraph(attributePaths = {"region"})
    Page<Ritual> findAll(Specification<Ritual> spec, Pageable pageable);

    // Lấy tất cả + kèm region (tránh LazyInitializationException)
    @EntityGraph(attributePaths = {"region"})
    @Query("select r from Ritual r")
    List<Ritual> findAllWithRegion();

    // Lấy 1 ritual + kèm region
    @EntityGraph(attributePaths = {"region"})
    @Query("select r from Ritual r where r.ritualId = :id")
    Optional<Ritual> findByIdWithRegion(@Param("id") Long id);

    // Search theo tên + kèm region
    @EntityGraph(attributePaths = {"region"})
    @Query("""
        select r from Ritual r
        where lower(r.ritualName) like lower(concat('%', :keyword, '%'))
        """)
    List<Ritual> searchByName(@Param("keyword") String keyword);
}
