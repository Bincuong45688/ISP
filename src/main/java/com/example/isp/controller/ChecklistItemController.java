package com.example.isp.controller;

import com.example.isp.config.SecurityRoles;
import com.example.isp.model.ChecklistItem;
import com.example.isp.service.ChecklistItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/checklist-items")
@RequiredArgsConstructor
@Tag(name = "Checklist Item Management", description = "Quản lý vật phẩm checklist")
public class ChecklistItemController {

    private final ChecklistItemService service;

    // 📌 Public: Lấy toàn bộ item
    @Operation(summary = "Lấy toàn bộ checklist item")
    @GetMapping
    public ResponseEntity<List<ChecklistItem>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @Operation(summary = "Lấy checklist item theo ID")
    @GetMapping("/{id}")
    public ResponseEntity<ChecklistItem> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    // 🧑‍💼 STAFF: Quản trị CRUD
    @Operation(summary = "Tạo checklist item", security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize(SecurityRoles.STAFF)
    @PostMapping
    public ResponseEntity<ChecklistItem> create(@RequestBody ChecklistItem item) {
        return ResponseEntity.ok(service.create(item));
    }

    @Operation(summary = "Cập nhật checklist item", security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize(SecurityRoles.STAFF)
    @PutMapping("/{id}")
    public ResponseEntity<ChecklistItem> update(@PathVariable Long id, @RequestBody ChecklistItem item) {
        return ResponseEntity.ok(service.update(id, item));
    }

    @Operation(summary = "Xóa checklist item", security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize(SecurityRoles.STAFF)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
