package com.example.isp.service;

import java.util.List;

public interface UserChecklistService {
    UserChecklist createChecklist(UserChecklist checklist);
    List<UserChecklist> getByUser(Long userId);
    void deleteChecklist(Long id);
}
