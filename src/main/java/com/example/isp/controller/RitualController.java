package com.example.isp.controller;

import com.example.isp.config.SecurityRoles;
import com.example.isp.dto.RitualUpdateDTO;
import com.example.isp.dto.request.RitualRequestDTO;
import com.example.isp.model.Region;
import com.example.isp.model.enums.Ritual;
import com.example.isp.repository.RegionRepository;
import com.example.isp.service.ImageUploadService;
import com.example.isp.service.RitualService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/ritual")
@RequiredArgsConstructor
@Tag(name = "Ritual Management", description = "Quản lý nghi lễ — xem, tạo, sửa, xóa, cập nhật ảnh")
public class RitualController {

    private final RitualService ritualService;
    private final RegionRepository regionRepository;
    private final ImageUploadService imageUploadService;

    // ================= PUBLIC =================

    @Operation(summary = "Lấy tất cả nghi lễ", description = "Public API")
    @GetMapping
    public ResponseEntity<List<Ritual>> getAll() {
        return ResponseEntity.ok(ritualService.getAllRituals());
    }

    @Operation(summary = "Lấy nghi lễ theo ID", description = "Public API")
    @GetMapping("/{id}")
    public ResponseEntity<Ritual> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ritualService.getRitualById(id));
    }

    @Operation(summary = "Lấy nghi lễ theo vùng miền", description = "Public API")
    @GetMapping("/region/{region}")
    public ResponseEntity<List<Ritual>> getByRegion(@PathVariable String region) {
        return ResponseEntity.ok(ritualService.getRitualsByRegion(region));
    }

    // ================= STAFF =================

    @Operation(
            summary = "Tạo nghi lễ (STAFF)",
            description = "Cho phép STAFF tạo nghi lễ mới — tự động liên kết vùng miền, có thể kèm ảnh.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PreAuthorize(SecurityRoles.STAFF)
    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<Ritual> create(@ModelAttribute RitualRequestDTO dto) throws IOException {
        Region region = regionRepository.findById(dto.getRegionId())
                .orElseThrow(() -> new EntityNotFoundException("Region not found with id: " + dto.getRegionId()));

        Ritual ritual = new Ritual();
        ritual.setRitualName(dto.getRitualName());
        ritual.setDescription(dto.getDescription());
        ritual.setRegion(region);
        ritual.setDateLunar(dto.getDateLunar());
        ritual.setDateSolar(dto.getDateSolar());
        ritual.setMeaning(dto.getMeaning());
        ritual.setActive(dto.isActive());

        MultipartFile file = dto.getFile();
        if (file != null && !file.isEmpty()) {
            String imageUrl = imageUploadService.uploadImage(file);
            ritual.setImageUrl(imageUrl);
        }

        return ResponseEntity.ok(ritualService.createRitual(ritual));
    }

    @Operation(
            summary = "Cập nhật nghi lễ (STAFF)",
            description = "Chỉ tài khoản STAFF được chỉnh sửa nghi lễ.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PreAuthorize(SecurityRoles     .STAFF)
    @PutMapping("/{id}")
    public ResponseEntity<Ritual> update(@PathVariable Long id, @RequestBody RitualUpdateDTO dto) {
        Region region = regionRepository.findById(dto.getRegionId())
                .orElseThrow(() -> new EntityNotFoundException("Region not found with id: " + dto.getRegionId()));

        Ritual ritual = new Ritual();
        ritual.setRitualName(dto.getRitualName());
        ritual.setDescription(dto.getDescription());
        ritual.setRegion(region);
        ritual.setDateLunar(dto.getDateLunar());
        ritual.setDateSolar(dto.getDateSolar());
        ritual.setMeaning(dto.getMeaning());
        ritual.setActive(dto.isActive());
        ritual.setImageUrl(dto.getImageUrl());

        return ResponseEntity.ok(ritualService.updateRitual(id, ritual));
    }

    @Operation(
            summary = "Cập nhật ảnh nghi lễ (STAFF)",
            description = "Chỉ thay đổi ảnh đại diện nghi lễ.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PreAuthorize(SecurityRoles.STAFF)
    @PutMapping(value = "/{id}/upload-image", consumes = {"multipart/form-data"})
    public ResponseEntity<Ritual> updateImage(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        if (file == null || file.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        String imageUrl = imageUploadService.uploadImage(file);
        Ritual updated = ritualService.updateRitualImage(id, imageUrl);
        return ResponseEntity.ok(updated);
    }

    @Operation(
            summary = "Xóa nghi lễ (STAFF)",
            description = "Chỉ tài khoản STAFF được xóa nghi lễ.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PreAuthorize(SecurityRoles.STAFF)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        ritualService.deleteRitual(id);
        return ResponseEntity.noContent().build();
    }
}
