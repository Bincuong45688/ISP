package com.example.isp.controller;

import com.example.isp.config.SecurityRoles;
import com.example.isp.dto.RitualCreateDTO;
import com.example.isp.dto.RitualUpdateDTO;
import com.example.isp.model.enums.Ritual;
import com.example.isp.service.RitualService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ritual")
@RequiredArgsConstructor
@Tag(name = "Ritual Management", description = "Quản lý nghi lễ — xem, tạo, sửa, xóa")
public class RitualController {

    private final RitualService ritualService;

    // 👤 Public
    @Operation(
            summary = "Lấy tất cả nghi lễ (Public)",
            description = "Trả về danh sách tất cả nghi lễ đang hoạt động. Người dùng không cần đăng nhập."
    )
    @GetMapping
    public ResponseEntity<List<Ritual>> getAll() {
        return ResponseEntity.ok(ritualService.getAllRituals());
    }

    @Operation(
            summary = "Lấy nghi lễ theo ID (Public)",
            description = "Trả về thông tin chi tiết của một nghi lễ theo ID."
    )
    @GetMapping("/{id}")
    public ResponseEntity<Ritual> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ritualService.getRitualById(id));
    }

    @Operation(
            summary = "Lấy nghi lễ theo vùng miền (Public)",
            description = "Trả về danh sách nghi lễ theo vùng miền. Người dùng không cần đăng nhập."
    )
    @GetMapping("/region/{region}")
    public ResponseEntity<List<Ritual>> getByRegion(@PathVariable String region) {
        return ResponseEntity.ok(ritualService.getRitualsByRegion(region));
    }


    @Operation(
            summary = "Tạo nghi lễ mới (STAFF)",
            description = "Chỉ tài khoản có quyền STAFF mới được phép tạo nghi lễ mới.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PreAuthorize(SecurityRoles.STAFF)
    @PostMapping
    public ResponseEntity<Ritual> create(@RequestBody RitualCreateDTO dto) {
        Ritual ritual = new Ritual();
        ritual.setRitualName(dto.getRitualName());
        ritual.setDescription(dto.getDescription());
        ritual.setRegion(dto.getRegion());
        ritual.setActive(dto.isActive());
        return ResponseEntity.ok(ritualService.createRitual(ritual));
    }

    @Operation(
            summary = "Cập nhật nghi lễ (STAFF)",
            description = "Chỉ tài khoản có quyền STAFF mới được phép chỉnh sửa nghi lễ.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PreAuthorize(SecurityRoles.STAFF)
    @PutMapping("/{id}")
    public ResponseEntity<Ritual> update(@PathVariable Long id, @RequestBody RitualUpdateDTO dto) {
        Ritual ritual = new Ritual();
        ritual.setRitualName(dto.getRitualName());
        ritual.setDescription(dto.getDescription());
        ritual.setRegion(dto.getRegion());
        ritual.setActive(dto.isActive());
        return ResponseEntity.ok(ritualService.updateRitual(id, ritual));
    }

    @Operation(
            summary = "Xóa nghi lễ (STAFF)",
            description = "Chỉ tài khoản có quyền STAFF mới được phép xóa nghi lễ.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PreAuthorize(SecurityRoles.STAFF)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        ritualService.deleteRitual(id);
        return ResponseEntity.noContent().build();
    }
}
