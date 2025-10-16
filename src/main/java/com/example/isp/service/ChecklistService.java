package com.example.isp.service;

import com.example.isp.dto.ChecklistDTO;
import java.util.List;

public interface ChecklistService {
    List<ChecklistDTO> getByRitual(Long ritualId);
    ChecklistDTO addChecklist(ChecklistDTO dto);
    ChecklistDTO updateChecklist(Long id, ChecklistDTO dto);
    void deleteChecklist(Long id);
}
