package com.example.isp.controller;


import com.example.isp.model.ChecklistItem;
import com.example.isp.repository.ChecklistItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/checklist-items")
@RequiredArgsConstructor
public class ChecklistItemController {

    private final ChecklistItemRepository itemRepository;

    @GetMapping
    public List<ChecklistItem> getAll() {
        return itemRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChecklistItem> getById(@PathVariable Long id) {
        return itemRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ChecklistItem create(@RequestBody ChecklistItem item) {
        return itemRepository.save(item);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChecklistItem> update(@PathVariable Long id, @RequestBody ChecklistItem updated) {
        return itemRepository.findById(id)
                .map(item -> {
                    item.setItemName(updated.getItemName());
                    item.setItemDescription(updated.getItemDescription());
                    item.setUnit(updated.getUnit());
                    return ResponseEntity.ok(itemRepository.save(item));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        itemRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}