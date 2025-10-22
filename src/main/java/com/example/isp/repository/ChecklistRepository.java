package com.example.isp.repository;

import com.example.isp.model.Checklist;
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
public interface ChecklistRepository extends JpaRepository<Checklist, Long>, JpaSpecificationExecutor<Checklist> {

    // Cho phép dùng Specification (lọc theo ritual, item)
    @Override
    @EntityGraph(attributePaths = {"ritual", "item"})
    Page<Checklist> findAll(Specification<Checklist> spec, Pageable pageable);

    // Lấy tất cả + kèm ritual và item (tránh LazyInitializationException)
    @EntityGraph(attributePaths = {"ritual", "item"})
    @Query("select c from Checklist c")
    List<Checklist> findAllWithRelations();

    // Lấy 1 checklist + kèm ritual và item
    @EntityGraph(attributePaths = {"ritual", "item"})
    @Query("select c from Checklist c where c.checklistId = :id")
    Optional<Checklist> findByIdWithRelations(@Param("id") Long id);

    // Tìm theo ritual ID
    @EntityGraph(attributePaths = {"ritual", "item"})
    @Query("select c from Checklist c where c.ritual.ritualId = :ritualId")
    List<Checklist> findByRitualId(@Param("ritualId") Long ritualId);

    // Tìm theo item ID
    @EntityGraph(attributePaths = {"ritual", "item"})
    @Query("select c from Checklist c where c.item.itemId = :itemId")
    List<Checklist> findByItemId(@Param("itemId") Long itemId);
}
