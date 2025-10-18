package com.example.isp.service;

import com.example.isp.model.UserChecklistItem;
import java.util.List;

public interface UserChecklistItemService {
    UserChecklistItem addItem(UserChecklistItem item);
    List<UserChecklistItem> getItemsByChecklist(Long checklistId);
    void deleteItem(Long id);
}
