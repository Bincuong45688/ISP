package com.example.isp.service;


import com.example.isp.model.enums.Checklist;

import java.util.List;

public interface ChecklistService {
    List<Checklist> getByRitual(Long ritualId);
    Checklist addChecklist(Checklist checklist);
    void deleteChecklist(Long id);
}