package com.example.isp.repository;

import com.example.isp.model.UserChecklistItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserChecklistItemRepository extends JpaRepository<UserChecklistItem, Long> {

    // Find all items by user checklist
    // Fix LazyInitializationException: Load ChecklistItem with JOIN FETCH
    @Query("SELECT uci FROM UserChecklistItem uci " +
           "JOIN FETCH uci.item " +
           "WHERE uci.userChecklist.userChecklistId = :userChecklistId")
    List<UserChecklistItem> findByUserChecklist_UserChecklistId(@Param("userChecklistId") Long userChecklistId);

    // Find specific item by userChecklistId and itemId
    // Fix LazyInitializationException: Load ChecklistItem with JOIN FETCH
    @Query("SELECT uci FROM UserChecklistItem uci " +
           "JOIN FETCH uci.item " +
           "WHERE uci.userChecklist.userChecklistId = :userChecklistId " +
           "AND uci.item.itemId = :itemId")
    java.util.Optional<UserChecklistItem> findByUserChecklist_UserChecklistIdAndItem_ItemId(
            @Param("userChecklistId") Long userChecklistId, 
            @Param("itemId") Long itemId);

    // Delete all items by user checklist
    void deleteByUserChecklist_UserChecklistId(Long userChecklistId);
}
