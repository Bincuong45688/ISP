package com.example.isp.controller;


import com.example.isp.dto.ChecklistDTO;
import com.example.isp.model.enums.Checklist;
import com.example.isp.service.ChecklistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/checklist")
@RequiredArgsConstructor
public class ChecklistController {

    private final ChecklistService checklistService;

    @GetMapping("/ritual/{ritualId}")
    public ResponseEntity<List<ChecklistDTO>> getByRitual(@PathVariable Long ritualId) {
        List<ChecklistDTO> result = checklistService.getByRitual(ritualId)
                .stream()
                .map(c -> new ChecklistDTO(
                        c.getChecklistId(),
                        c.getRitual().getId(),
                        c.getItem().getItemId(),
                        c.getQuantity(),
                        c.getCheckNote()
                ))
                .toList();

        return ResponseEntity.ok(result);
    }


    @PostMapping
    public ResponseEntity<Checklist> addChecklist(@RequestBody Checklist checklist) {
        return ResponseEntity.ok(checklistService.addChecklist(checklist));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChecklist(@PathVariable Long id) {
        checklistService.deleteChecklist(id);
        return ResponseEntity.noContent().build();
    }
}