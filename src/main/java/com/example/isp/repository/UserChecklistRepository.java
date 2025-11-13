package com.example.isp.repository;

import com.example.isp.model.UserChecklist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UserChecklistRepository extends JpaRepository<UserChecklist, Long> {

    // Find all checklists by user
    Page<UserChecklist> findByUser_CustomerId(Long userId, Pageable pageable);

    // Find by user and ritual
    Page<UserChecklist> findByUser_CustomerIdAndRitual_RitualId(Long userId, Long ritualId, Pageable pageable);

    // Find by user and title containing
    Page<UserChecklist> findByUser_CustomerIdAndTitleContainingIgnoreCase(Long userId, String title, Pageable pageable);

    // Find checklists that need notification (only active)
    // JOIN FETCH to avoid LazyInitializationException when accessing user, account, and ritual
    @Query("SELECT uc FROM UserChecklist uc " +
           "JOIN FETCH uc.user u " +
           "LEFT JOIN FETCH u.account " +
           "JOIN FETCH uc.ritual " +
           "WHERE uc.isNotified = false AND uc.reminderDate <= :now AND uc.isActive = true")
    List<UserChecklist> findChecklistsNeedingNotification(@Param("now") LocalDateTime now);

    // Find by user with filters (only active)
    // JOIN FETCH to avoid LazyInitializationException
    @Query("SELECT DISTINCT uc FROM UserChecklist uc " +
            "JOIN FETCH uc.user " +
            "JOIN FETCH uc.ritual " +
            "WHERE uc.user.customerId = :userId " +
            "AND (:ritualId IS NULL OR uc.ritual.ritualId = :ritualId) " +
            "AND (:title IS NULL OR LOWER(uc.title) LIKE LOWER(CONCAT('%', :title, '%'))) " +
            "AND uc.isActive = true")
    Page<UserChecklist> findByUserWithFilters(
            @Param("userId") Long userId,
            @Param("ritualId") Long ritualId,
            @Param("title") String title,
            Pageable pageable
    );

    // Find all active checklists
    // JOIN FETCH to avoid LazyInitializationException
    @Query("SELECT uc FROM UserChecklist uc " +
           "JOIN FETCH uc.user " +
           "JOIN FETCH uc.ritual " +
           "WHERE uc.isActive = true")
    List<UserChecklist> findAllActive();

    // Find by ID and active
    // JOIN FETCH to avoid LazyInitializationException
    @Query("SELECT uc FROM UserChecklist uc " +
           "JOIN FETCH uc.user " +
           "JOIN FETCH uc.ritual " +
           "WHERE uc.userChecklistId = :id AND uc.isActive = true")
    java.util.Optional<UserChecklist> findByIdAndActive(@Param("id") Long id);

    // Find by user (only active)
    // JOIN FETCH to avoid LazyInitializationException
    @Query("SELECT DISTINCT uc FROM UserChecklist uc " +
           "JOIN FETCH uc.user " +
           "JOIN FETCH uc.ritual " +
           "WHERE uc.user.customerId = :userId AND uc.isActive = true")
    Page<UserChecklist> findByUserIdAndActive(@Param("userId") Long userId, Pageable pageable);

    // Find by ID (including deleted ones, for restore function)
    // JOIN FETCH to avoid LazyInitializationException
    @Query("SELECT uc FROM UserChecklist uc " +
           "JOIN FETCH uc.user " +
           "JOIN FETCH uc.ritual " +
           "WHERE uc.userChecklistId = :id")
    java.util.Optional<UserChecklist> findByIdWithRelations(@Param("id") Long id);
}
