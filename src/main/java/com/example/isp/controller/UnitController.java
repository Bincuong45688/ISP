package com.example.isp.controller;

import com.example.isp.dto.request.CreateUnitRequest;
import com.example.isp.dto.request.UpdateUnitRequest;
import com.example.isp.dto.response.UnitResponse;
import com.example.isp.model.enums.Unit;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/units")
@RequiredArgsConstructor
public class UnitController {

    // ==================== CRUD Operations ====================

    /**
     * Lấy danh sách tất cả đơn vị
     */

    /**
     * Lấy danh sách enum units (để tương thích ngược)
     */
    @GetMapping("/enums")
    public List<Map<String, String>> getAllEnumUnits() {
        return Arrays.stream(Unit.values())
                .map(unit -> Map.of(
                        "name", unit.name(),
                        "displayName", unit.getDisplayName()
                ))
                .collect(Collectors.toList());
    }

    /**
     * Lấy enum unit theo tên
     */
    @GetMapping("/enums/{name}")
    public ResponseEntity<Map<String, String>> getEnumUnitByName(@PathVariable String name) {
        try {
            Unit unit = Unit.valueOf(name.toUpperCase());
            Map<String, String> response = new HashMap<>();
            response.put("name", unit.name());
            response.put("displayName", unit.getDisplayName());
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Validate enum unit
     */
    @GetMapping("/enums/validate/{name}")
    public ResponseEntity<Map<String, Object>> validateEnumUnit(@PathVariable String name) {
        Map<String, Object> response = new HashMap<>();
        try {
            Unit unit = Unit.valueOf(name.toUpperCase());
            response.put("valid", true);
            response.put("name", unit.name());
            response.put("displayName", unit.getDisplayName());
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.put("valid", false);
            response.put("message", "Unit không tồn tại: " + name);
            return ResponseEntity.ok(response);
        }
    }

    /**
     * Tìm kiếm enum units
     */
    @GetMapping("/enums/search")
    public List<Map<String, String>> searchEnumUnits(@RequestParam String q) {
        String searchTerm = q.toLowerCase();
        return Arrays.stream(Unit.values())
                .filter(unit -> unit.getDisplayName().toLowerCase().contains(searchTerm) ||
                        unit.name().toLowerCase().contains(searchTerm))
                .map(unit -> Map.of(
                        "name", unit.name(),
                        "displayName", unit.getDisplayName()
                ))
                .collect(Collectors.toList());
    }

    // ==================== Helper Methods ====================

}
