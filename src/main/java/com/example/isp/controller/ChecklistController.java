package com.example.isp.controller;

import com.example.isp.dto.ChecklistDTO;
import com.example.isp.service.ChecklistService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/checklist")
@RequiredArgsConstructor
@Tag(name = "Checklist Management", description = "Quản lý danh sách vật phẩm của nghi lễ")
public class ChecklistController {

    private final ChecklistService checklistService;

    @Operation(summary = "Lấy checklist theo nghi lễ (Public)")
    @GetMapping("/ritual/{ritualId}")
    public ResponseEntity<List<ChecklistDTO>> getByRitual(@PathVariable Long ritualId) {
        return ResponseEntity.ok(checklistService.getByRitual(ritualId));
    }

    @Operation(
            summary = "Thêm vật phẩm vào checklist (STAFF)",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PreAuthorize("hasRole('STAFF')")
    @PostMapping
    public ResponseEntity<ChecklistDTO> addChecklist(@RequestBody ChecklistDTO dto) {
        return ResponseEntity.ok(checklistService.addChecklist(dto));
    }

    @Operation(
            summary = "Cập nhật checklist (STAFF)",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PreAuthorize("hasRole('STAFF')")
    @PutMapping("/{id}")
    public ResponseEntity<ChecklistDTO> updateChecklist(@PathVariable Long id, @RequestBody ChecklistDTO dto) {
        return ResponseEntity.ok(checklistService.updateChecklist(id, dto));
    }

    @Operation(
            summary = "Xóa checklist (STAFF)",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PreAuthorize("hasRole('STAFF')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChecklist(@PathVariable Long id) {
        checklistService.deleteChecklist(id);
        return ResponseEntity.noContent().build();
    }
}
