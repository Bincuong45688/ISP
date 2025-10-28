package com.example.isp.controller;

import com.example.isp.dto.CreateUserChecklistItemRequest;
import com.example.isp.dto.UpdateUserChecklistItemRequest;
import com.example.isp.dto.UserChecklistItemDTO;
import com.example.isp.service.UserChecklistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user-checklist-items")
@RequiredArgsConstructor
public class UserChecklistItemController {

    private final UserChecklistService userChecklistService;

    /**
     * Get all items for a specific user checklist
     * GET /api/user-checklist-items?userChecklistId={id}
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getUserChecklistItems(
            @RequestParam Long userChecklistId
    ) {
        try {
            List<UserChecklistItemDTO> items = userChecklistService.getUserChecklistItems(userChecklistId);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", items);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Failed to get user checklist items: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    /**
     * Get a specific user checklist item by ID
     * GET /api/user-checklist-items/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getUserChecklistItemById(@PathVariable Long id) {
        try {
            UserChecklistItemDTO item = userChecklistService.getUserChecklistItemById(id);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", item);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "User checklist item not found: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    /**
     * Add a new item to user's checklist
     * POST /api/user-checklist-items
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Map<String, Object>> createUserChecklistItem(
            @RequestBody CreateUserChecklistItemRequest request
    ) {
        try {
            UserChecklistItemDTO item = userChecklistService.createUserChecklistItem(request);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "User checklist item created successfully");
            response.put("data", item);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Failed to create user checklist item: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    /**
     * Update user checklist item (quantity, checked status, note)
     * PUT /api/user-checklist-items/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateUserChecklistItem(
            @PathVariable Long id,
            @RequestBody UpdateUserChecklistItemRequest request
    ) {
        try {
            UserChecklistItemDTO item = userChecklistService.updateUserChecklistItem(id, request);
            
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
     * Delete a user checklist item
     * DELETE /api/user-checklist-items/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteUserChecklistItem(@PathVariable Long id) {
        try {
            userChecklistService.deleteUserChecklistItem(id);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "User checklist item deleted successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Failed to delete user checklist item: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    /**
     * Mark item as completed/checked
     * PUT /api/user-checklist-items/{id}/check
     */
    @PutMapping("/{id}/check")
    public ResponseEntity<Map<String, Object>> checkUserChecklistItem(
            @PathVariable Long id,
            @RequestParam(defaultValue = "true") Boolean checked
    ) {
        try {
            UserChecklistItemDTO item = userChecklistService.checkUserChecklistItem(id, checked);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", checked ? "Item marked as completed" : "Item marked as incomplete");
            response.put("data", item);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Failed to update item status: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
