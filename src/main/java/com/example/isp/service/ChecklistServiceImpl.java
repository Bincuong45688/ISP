package com.example.isp.service;

import com.example.isp.dto.ChecklistDTO;
import com.example.isp.model.ChecklistItem;
import com.example.isp.model.enums.Checklist;
import com.example.isp.model.enums.Ritual;
import com.example.isp.repository.ChecklistItemRepository;
import com.example.isp.repository.ChecklistRepository;
import com.example.isp.repository.RitualRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChecklistServiceImpl implements ChecklistService {

    private final ChecklistRepository checklistRepository;
    private final RitualRepository ritualRepository;
    private final ChecklistItemRepository checklistItemRepository;

    @Override
    public List<ChecklistDTO> getByRitual(Long ritualId) {
        return checklistRepository.findByRitualId(ritualId)
                .stream()
                .map(c -> new ChecklistDTO(
                        c.getChecklistId(),
                        c.getRitual().getId(),
                        c.getItem().getItemId(),
                        c.getQuantity(),
                        c.getCheckNote()
                ))
                .toList();
    }

    @Override
    public ChecklistDTO addChecklist(ChecklistDTO dto) {
        Ritual ritual = ritualRepository.findById(dto.getRitualId())
                .orElseThrow(() -> new RuntimeException("Ritual not found"));
        ChecklistItem item = checklistItemRepository.findById(dto.getItemId())
                .orElseThrow(() -> new RuntimeException("Item not found"));

        Checklist checklist = Checklist.builder()
                .ritual(ritual)
                .item(item)
                .quantity(dto.getQuantity())
                .checkNote(dto.getCheckNote())
                .build();

        Checklist saved = checklistRepository.save(checklist);
        return new ChecklistDTO(
                saved.getChecklistId(),
                saved.getRitual().getId(),
                saved.getItem().getItemId(),
                saved.getQuantity(),
                saved.getCheckNote()
        );
    }

    @Override
    public ChecklistDTO updateChecklist(Long id, ChecklistDTO dto) {
        Checklist checklist = checklistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Checklist not found"));
        checklist.setQuantity(dto.getQuantity());
        checklist.setCheckNote(dto.getCheckNote());
        checklistRepository.save(checklist);

        return new ChecklistDTO(
                checklist.getChecklistId(),
                checklist.getRitual().getId(),
                checklist.getItem().getItemId(),
                checklist.getQuantity(),
                checklist.getCheckNote()
        );
    }

    @Override
    public void deleteChecklist(Long id) {
        checklistRepository.deleteById(id);
    }
}
