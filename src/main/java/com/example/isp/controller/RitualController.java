package com.example.isp.controller;


import com.example.isp.model.enums.Ritual;
import com.example.isp.service.RitualService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ritual")
@RequiredArgsConstructor
public class RitualController {

    private final RitualService ritualService;

    @GetMapping
    public ResponseEntity<List<Ritual>> getAll() {
        return ResponseEntity.ok(ritualService.getAllRituals());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ritual> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ritualService.getRitualById(id));
    }

    @GetMapping("/region/{region}")
    public ResponseEntity<List<Ritual>> getByRegion(@PathVariable String region) {
        return ResponseEntity.ok(ritualService.getRitualsByRegion(region));
    }

    @PostMapping
    public ResponseEntity<Ritual> create(@RequestBody Ritual ritual) {
        return ResponseEntity.ok(ritualService.createRitual(ritual));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ritual> update(@PathVariable Long id, @RequestBody Ritual ritual) {
        return ResponseEntity.ok(ritualService.updateRitual(id, ritual));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        ritualService.deleteRitual(id);
        return ResponseEntity.noContent().build();
    }
}