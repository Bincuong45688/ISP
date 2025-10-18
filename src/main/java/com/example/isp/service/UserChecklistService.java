package com.example.isp.service;

import com.example.isp.model.UserChecklist;
import java.util.List;

public interface UserChecklistService {
    UserChecklist createChecklist(UserChecklist checklist);
    List<UserChecklist> getByUser(Long userId);
    void deleteChecklist(Long id);
}
