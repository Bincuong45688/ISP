package com.example.isp.controller;

import com.example.isp.dto.request.CreateUserChecklistRequest;
import com.example.isp.dto.request.UpdateUserChecklistItemRequest;
import com.example.isp.dto.response.UserChecklistDTO;
import com.example.isp.dto.response.UserChecklistItemDTO;
import com.example.isp.service.UserChecklistService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user-checklists")
@RequiredArgsConstructor
public class UserChecklistController {

    private final UserChecklistService userChecklistService;

    /**
     * Create a new user checklist by copying from ritual's checklist
     * POST /api/user-checklists
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> createUserChecklist(@RequestBody CreateUserChecklistRequest request) {
        try {
            UserChecklistDTO checklist = userChecklistService.createUserChecklist(request);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "User checklist created successfully");
            response.put("data", checklist);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Failed to create user checklist: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    /**
     * Get user checklist by ID
     * GET /api/user-checklists/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getUserChecklistById(@PathVariable Long id) {
        try {
            UserChecklistDTO checklist = userChecklistService.getUserChecklistById(id);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", checklist);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "User checklist not found: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    /**
     * Get all user checklists with filters and paging
     * GET /api/user-checklists?userId=1&ritualId=2&title=giỗ&page=0&size=10&sort=createdAt,desc
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getUserChecklists(
            @RequestParam Long userId,
            @RequestParam(required = false) Long ritualId,
            @RequestParam(required = false) String title,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt,desc") String[] sort
    ) {
        try {
            // Parse sort parameters
            String sortField = sort[0];
            String sortDirection = sort.length > 1 ? sort[1] : "desc";
            Sort.Direction direction = sortDirection.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;

            Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortField));

            Page<UserChecklistDTO> checklistPage = userChecklistService.getUserChecklists(userId, ritualId, title, pageable);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", checklistPage.getContent());
            response.put("currentPage", checklistPage.getNumber());
            response.put("totalItems", checklistPage.getTotalElements());
            response.put("totalPages", checklistPage.getTotalPages());
            response.put("pageSize", checklistPage.getSize());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Failed to get user checklists: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    /**
     * Update user checklist (title, reminder date)
     * PUT /api/user-checklists/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateUserChecklist(
            @PathVariable Long id,
            @RequestBody Map<String, Object> updates
    ) {
        try {
            String title = (String) updates.get("title");
            LocalDateTime reminderDate = updates.get("reminderDate") != null
                    ? LocalDateTime.parse((String) updates.get("reminderDate"))
                    : null;

            UserChecklistDTO checklist = userChecklistService.updateUserChecklist(id, title, reminderDate);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "User checklist updated successfully");
            response.put("data", checklist);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Failed to update user checklist: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    /**
     * Update user checklist item (quantity, checked, note)
     * PUT /api/user-checklists/items/{itemId}
     */
    @PutMapping("/items/{itemId}")
    public ResponseEntity<Map<String, Object>> updateUserChecklistItem(
            @PathVariable Long itemId,
            @RequestBody UpdateUserChecklistItemRequest request
    ) {
        try {
            UserChecklistItemDTO item = userChecklistService.updateUserChecklistItem(itemId, request);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "User checklist item updated successfully");
            response.put("data", item);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Failed to update user checklist item: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    /**
     * Delete user checklist
     * DELETE /api/user-checklists/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteUserChecklist(@PathVariable Long id) {
        try {
            userChecklistService.deleteUserChecklist(id);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "User checklist deleted successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Failed to delete user checklist: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    /**
     * Checkout user checklist - deduct stock from inventory
     * POST /api/user-checklists/{id}/checkout
     */
    @PostMapping("/{id}/checkout")
    public ResponseEntity<Map<String, Object>> checkoutUserChecklist(@PathVariable Long id) {
        try {
            userChecklistService.checkoutUserChecklist(id);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Checkout successful. Stock quantities have been updated.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Checkout failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    /**
     * Add stock to item inventory
     * POST /api/user-checklists/items/{itemId}/add-stock
     */
    @PostMapping("/items/{itemId}/add-stock")
    public ResponseEntity<Map<String, Object>> addStock(
            @PathVariable Long itemId,
            @RequestBody Map<String, Integer> request
    ) {
        try {
            Integer quantity = request.get("quantity");
            if (quantity == null || quantity <= 0) {
                throw new RuntimeException("Quantity must be greater than 0");
            }

            userChecklistService.addStock(itemId, quantity);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Stock added successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Failed to add stock: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
