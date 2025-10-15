package com.example.isp.service;


import com.example.isp.model.enums.Checklist;
import com.example.isp.repository.ChecklistRepository;
import com.example.isp.service.ChecklistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChecklistServiceImpl implements ChecklistService {

    private final ChecklistRepository checklistRepository;

    @Override
    public List<Checklist> getByRitual(Long ritualId) {
        List<Checklist> result = checklistRepository.findByRitual_Id(ritualId);
        return result != null ? result : List.of();
    }

    @Override
    public Checklist addChecklist(Checklist checklist) {
        return checklistRepository.save(checklist);
    }

    @Override
    public void deleteChecklist(Long id) {
        checklistRepository.deleteById(id);
    }
}