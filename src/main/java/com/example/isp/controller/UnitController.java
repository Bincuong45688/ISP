package com.example.isp.controller;

import com.example.isp.model.enums.Unit;
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
public class UnitController {

    // ==== Get all units ====
    @GetMapping
    public List<Map<String, String>> getAllUnits() {
        return Arrays.stream(Unit.values())
                .map(unit -> Map.of(
                        "name", unit.name(),
                        "displayName", unit.getDisplayName()
                ))
                .collect(Collectors.toList());
    }

    // ==== Get unit by name ====
    @GetMapping("/{name}")
    public ResponseEntity<Map<String, String>> getUnitByName(@PathVariable String name) {
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

    // ==== Validate unit exists ====
    @GetMapping("/validate/{name}")
    public ResponseEntity<Map<String, Object>> validateUnit(@PathVariable String name) {
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

    // ==== Search units by display name ====
    @GetMapping("/search")
    public List<Map<String, String>> searchUnits(@RequestParam String q) {
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
}
