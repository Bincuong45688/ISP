package com.example.isp.service;


import com.example.isp.dto.request.CreateUserChecklistRequest;
import com.example.isp.dto.request.CreateUserChecklistItemRequest;
import com.example.isp.dto.request.UpdateUserChecklistItemRequest;
import com.example.isp.dto.response.UserChecklistDTO;
import com.example.isp.dto.response.UserChecklistItemDTO;


import com.example.isp.model.*;
import com.example.isp.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserChecklistService {

    private final UserChecklistRepository userChecklistRepository;
    private final UserChecklistItemRepository userChecklistItemRepository;
    private final CustomerRepository customerRepository;
    private final RitualRepository ritualRepository;
    private final ChecklistRepository checklistRepository;
    private final ChecklistItemRepository checklistItemRepository;

    /**
     * Create a personal checklist for user by copying from ritual's checklist
     */
    @Transactional
    public UserChecklistDTO createUserChecklist(CreateUserChecklistRequest request) {
        // Validate user
        Customer user = customerRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + request.getUserId()));

        // Validate ritual
        Ritual ritual = ritualRepository.findById(request.getRitualId())
                .orElseThrow(() -> new RuntimeException("Ritual not found with id: " + request.getRitualId()));

        // Create user checklist
        UserChecklist userChecklist = UserChecklist.builder()
                .user(user)
                .ritual(ritual)
                .title(request.getTitle())
                .reminderDate(request.getReminderDate())
                .createdAt(LocalDateTime.now())
                .isNotified(false)
                .build();

        userChecklist = userChecklistRepository.save(userChecklist);

        // Copy checklist items from ritual's checklist
        List<Checklist> ritualChecklists = checklistRepository.findByRitualId(request.getRitualId());

        for (Checklist checklist : ritualChecklists) {
            UserChecklistItem item = UserChecklistItem.builder()
                    .userChecklist(userChecklist)
                    .item(checklist.getItem())
                    .quantity(checklist.getQuantity())
                    .checked(false)
                    .note(checklist.getCheckNote())
                    .build();
            userChecklistItemRepository.save(item);
        }

        return convertToDTO(userChecklist);
    }

    /**
     * Get user checklist by ID with all items
     */
    public UserChecklistDTO getUserChecklistById(Long id) {
        UserChecklist userChecklist = userChecklistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User checklist not found with id: " + id));
        return convertToDTO(userChecklist);
    }

    /**
     * Get all user checklists with filters and paging
     */
    public Page<UserChecklistDTO> getUserChecklists(Long userId, Long ritualId, String title, Pageable pageable) {
        Page<UserChecklist> checklists = userChecklistRepository.findByUserWithFilters(userId, ritualId, title, pageable);
        return checklists.map(this::convertToDTO);
    }

    /**
     * Update user checklist (title, reminder date)
     */
    @Transactional
    public UserChecklistDTO updateUserChecklist(Long id, String title, LocalDateTime reminderDate) {
        UserChecklist userChecklist = userChecklistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User checklist not found with id: " + id));

        if (title != null) {
            userChecklist.setTitle(title);
        }
        if (reminderDate != null) {
            userChecklist.setReminderDate(reminderDate);
        }

        userChecklist = userChecklistRepository.save(userChecklist);
        return convertToDTO(userChecklist);
    }

    /**
     * Update user checklist item (quantity, checked status, note)
     * This allows user to modify their personal checklist without affecting the original
     */
    @Transactional
    public UserChecklistItemDTO updateUserChecklistItem(Long itemId, UpdateUserChecklistItemRequest request) {
        UserChecklistItem item = userChecklistItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("User checklist item not found with id: " + itemId));

        if (request.getQuantity() != null) {
            item.setQuantity(request.getQuantity());
        }
        if (request.getChecked() != null) {
            item.setChecked(request.getChecked());
        }
        if (request.getNote() != null) {
            item.setNote(request.getNote());
        }

        item = userChecklistItemRepository.save(item);
        return convertItemToDTO(item);
    }

    /**
     * Delete user checklist
     */
    @Transactional
    public void deleteUserChecklist(Long id) {
        if (!userChecklistRepository.existsById(id)) {
            throw new RuntimeException("User checklist not found with id: " + id);
        }
        userChecklistRepository.deleteById(id);
    }

    /**
     * Get checklists that need notification
     */
    public List<UserChecklistDTO> getChecklistsNeedingNotification() {
        List<UserChecklist> checklists = userChecklistRepository.findChecklistsNeedingNotification(LocalDateTime.now());
        return checklists.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Mark checklist as notified
     */
    @Transactional
    public void markAsNotified(Long id) {
        UserChecklist userChecklist = userChecklistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User checklist not found with id: " + id));
        userChecklist.setIsNotified(true);
        userChecklistRepository.save(userChecklist);
    }

    /**
     * Add stock to inventory (for restocking)
     */
    @Transactional
    public void addStock(Long itemId, Integer quantity) {
        ChecklistItem item = checklistItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found with id: " + itemId));

        Integer currentStock = item.getStockQuantity() != null ? item.getStockQuantity() : 0;
        item.setStockQuantity(currentStock + quantity);
        checklistItemRepository.save(item);
    }

    // Helper methods to convert entities to DTOs
    private UserChecklistDTO convertToDTO(UserChecklist userChecklist) {
        List<UserChecklistItem> items = userChecklistItemRepository
                .findByUserChecklist_UserChecklistId(userChecklist.getUserChecklistId());

        return UserChecklistDTO.builder()
                .userChecklistId(userChecklist.getUserChecklistId())
                .userId(userChecklist.getUser().getCustomerId())
                .userName(userChecklist.getUser().getCustomerName())
                .ritualId(userChecklist.getRitual().getRitualId())
                .ritualName(userChecklist.getRitual().getRitualName())
                .title(userChecklist.getTitle())
                .createdAt(userChecklist.getCreatedAt())
                .reminderDate(userChecklist.getReminderDate())
                .isNotified(userChecklist.getIsNotified())
                .items(items.stream().map(this::convertItemToDTO).collect(Collectors.toList()))
                .build();
    }

    private UserChecklistItemDTO convertItemToDTO(UserChecklistItem item) {
        return UserChecklistItemDTO.builder()
                .userChecklistItemId(item.getUserChecklistItemId())
                .itemId(item.getItem().getItemId())
                .itemName(item.getItem().getItemName())
                .unit(item.getItem().getUnit())
                .quantity(item.getQuantity())
                .checked(item.getChecked())
                .note(item.getNote())
                .stockQuantity(item.getItem().getStockQuantity())
                .build();
    }

    /**
     * Checkout user checklist - deduct stock quantity from inventory
     * This is called when user completes payment
     */
    @Transactional
    public void checkoutUserChecklist(Long userChecklistId) {
        UserChecklist userChecklist = userChecklistRepository.findById(userChecklistId)
                .orElseThrow(() -> new RuntimeException("User checklist not found with id: " + userChecklistId));

        List<UserChecklistItem> items = userChecklistItemRepository
                .findByUserChecklist_UserChecklistId(userChecklistId);

        for (UserChecklistItem userItem : items) {
            ChecklistItem item = userItem.getItem();
            Integer currentStock = item.getStockQuantity() != null ? item.getStockQuantity() : 0;
            Integer quantityToDeduct = userItem.getQuantity() != null ? userItem.getQuantity() : 0;

            // Check if stock is sufficient
            if (currentStock < quantityToDeduct) {
                throw new RuntimeException(
                        String.format("Insufficient stock for item '%s'. Available: %d, Required: %d",
                                item.getItemName(), currentStock, quantityToDeduct)
                );
            }

            // Deduct stock
            item.setStockQuantity(currentStock - quantityToDeduct);
            checklistItemRepository.save(item);
        }
    }

    /**
     * Get all items for a specific user checklist
     */
    public List<UserChecklistItemDTO> getUserChecklistItems(Long userChecklistId) {
        List<UserChecklistItem> items = userChecklistItemRepository
                .findByUserChecklist_UserChecklistId(userChecklistId);
        return items.stream()
                .map(this::convertItemToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get a specific user checklist item by ID
     */
    public UserChecklistItemDTO getUserChecklistItemById(Long id) {
        UserChecklistItem item = userChecklistItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User checklist item not found with id: " + id));
        return convertItemToDTO(item);
    }

    /**
     * Create a new user checklist item
     */
    @Transactional
    public UserChecklistItemDTO createUserChecklistItem(CreateUserChecklistItemRequest request) {
        // Validate user checklist
        UserChecklist userChecklist = userChecklistRepository.findById(request.getUserChecklistId())
                .orElseThrow(() -> new RuntimeException("User checklist not found with id: " + request.getUserChecklistId()));

        // Validate item
        ChecklistItem item = checklistItemRepository.findById(request.getItemId())
                .orElseThrow(() -> new RuntimeException("Item not found with id: " + request.getItemId()));

        // Create new user checklist item
        UserChecklistItem userChecklistItem = UserChecklistItem.builder()
                .userChecklist(userChecklist)
                .item(item)
                .quantity(request.getQuantity())
                .checked(false)
                .note(request.getNote())
                .build();

        userChecklistItem = userChecklistItemRepository.save(userChecklistItem);
        return convertItemToDTO(userChecklistItem);
    }

    /**
     * Delete a user checklist item
     */
    @Transactional
    public void deleteUserChecklistItem(Long id) {
        if (!userChecklistItemRepository.existsById(id)) {
            throw new RuntimeException("User checklist item not found with id: " + id);
        }
        userChecklistItemRepository.deleteById(id);
    }

    /**
     * Mark item as checked/unchecked
     */
    @Transactional
    public UserChecklistItemDTO checkUserChecklistItem(Long id, Boolean checked) {
        UserChecklistItem item = userChecklistItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User checklist item not found with id: " + id));

        item.setChecked(checked);
        item = userChecklistItemRepository.save(item);
        return convertItemToDTO(item);
    }
}
