package com.example.isp.controller;

import com.example.isp.dto.request.CreateUserChecklistItemRequest;
import com.example.isp.dto.request.UpdateUserChecklistItemRequest;
import com.example.isp.dto.response.UserChecklistItemDTO;
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
        List<UserChecklistItemDTO> items = userChecklistService.getUserChecklistItems(userChecklistId);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("data", items);
        return ResponseEntity.ok(response);
    }

    /**
     * Get a specific user checklist item by ID
     * GET /api/user-checklist-items/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getUserChecklistItemById(@PathVariable Long id) {
        UserChecklistItemDTO item = userChecklistService.getUserChecklistItemById(id);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("data", item);
        return ResponseEntity.ok(response);
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
        UserChecklistItemDTO item = userChecklistService.createUserChecklistItem(request);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Thêm item vào checklist thành công");
        response.put("data", item);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
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
        UserChecklistItemDTO item = userChecklistService.updateUserChecklistItem(id, request);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Cập nhật item thành công");
        response.put("data", item);
        return ResponseEntity.ok(response);
    }

    /**
     * Update user checklist item by userChecklistId and itemId
     * PUT /api/user-checklist-items/checklist/{userChecklistId}/item/{itemId}
     */
    @PutMapping("/checklist/{userChecklistId}/item/{itemId}")
    public ResponseEntity<Map<String, Object>> updateUserChecklistItemByIds(
            @PathVariable Long userChecklistId,
            @PathVariable Long itemId,
            @RequestBody UpdateUserChecklistItemRequest request
    ) {
        UserChecklistItemDTO item = userChecklistService.updateUserChecklistItemByIds(userChecklistId, itemId, request);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Cập nhật item thành công");
        response.put("data", item);
        return ResponseEntity.ok(response);
    }

    /**
     * Delete a user checklist item
     * DELETE /api/user-checklist-items/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteUserChecklistItem(@PathVariable Long id) {
        userChecklistService.deleteUserChecklistItem(id);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Xóa item thành công");
        return ResponseEntity.ok(response);
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
        UserChecklistItemDTO item = userChecklistService.checkUserChecklistItem(id, checked);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", checked ? "Đánh dấu hoàn thành" : "Bỏ đánh dấu hoàn thành");
        response.put("data", item);
        return ResponseEntity.ok(response);
    }
}
