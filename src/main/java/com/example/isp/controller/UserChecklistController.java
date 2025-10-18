package com.example.isp.controller;

import com.example.isp.model.UserChecklist;
import com.example.isp.service.UserChecklistService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-checklist")
@RequiredArgsConstructor
public class UserChecklistController {

    private final UserChecklistService userChecklistService;

    @Operation(
            summary = "Lấy tất cả checklist cá nhân của user",
            description = "USER có thể xem checklist của chính mình",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{userId}")
    public ResponseEntity<List<UserChecklist>> getByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(userChecklistService.getByUser(userId));
    }

    @Operation(
            summary = "Tạo checklist cá nhân",
            description = "USER có thể tạo checklist cá nhân của riêng mình",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ResponseEntity<UserChecklist> create(@RequestBody UserChecklist checklist) {
        return ResponseEntity.ok(userChecklistService.createChecklist(checklist));
    }

    @Operation(
            summary = "Xóa checklist cá nhân",
            description = "USER chỉ được xóa checklist của chính họ",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userChecklistService.deleteChecklist(id);
        return ResponseEntity.noContent().build();
    }
}
