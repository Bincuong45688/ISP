package com.example.isp.service;

import com.example.isp.model.ChecklistItem;

import java.util.List;

public interface ChecklistItemService {
    List<ChecklistItem> getAll();
    ChecklistItem getById(Long id);
    ChecklistItem create(ChecklistItem item);
    ChecklistItem update(Long id, ChecklistItem item);
    void delete(Long id);
}
