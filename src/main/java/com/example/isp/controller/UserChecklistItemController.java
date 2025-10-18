package com.example.isp.controller;

import com.example.isp.model.UserChecklistItem;
import com.example.isp.service.UserChecklistItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-checklist-items")
@RequiredArgsConstructor
public class UserChecklistItemController {

    private final UserChecklistItemService itemService;

    @Operation(
            summary = "Lấy danh sách vật phẩm trong checklist cá nhân",
            description = "USER có thể xem vật phẩm trong checklist của họ",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{userChecklistId}")
    public ResponseEntity<List<UserChecklistItem>> getItems(@PathVariable Long userChecklistId) {
        return ResponseEntity.ok(itemService.getItemsByChecklist(userChecklistId));
    }

    @Operation(
            summary = "Thêm vật phẩm vào checklist cá nhân",
            description = "USER có thể thêm/tích vật phẩm khi tạo checklist cá nhân",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ResponseEntity<UserChecklistItem> addItem(@RequestBody UserChecklistItem item) {
        return ResponseEntity.ok(itemService.addItem(item));
    }

    @Operation(
            summary = "Xóa vật phẩm trong checklist cá nhân",
            description = "USER có thể xóa vật phẩm họ đã thêm",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        itemService.deleteItem(id);
        return ResponseEntity.noContent().build();
    }
}
