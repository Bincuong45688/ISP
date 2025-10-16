package com.example.isp.service;

import com.example.isp.model.ChecklistItem;
import com.example.isp.repository.ChecklistItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ChecklistItemServiceImpl implements ChecklistItemService {

    private final ChecklistItemRepository repository;

    @Override
    public List<ChecklistItem> getAll() {
        return repository.findAll();
    }

    @Override
    public ChecklistItem getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Checklist Item not found with id: " + id));
    }

    @Override
    public ChecklistItem create(ChecklistItem item) {
        return repository.save(item);
    }

    @Override
    public ChecklistItem update(Long id, ChecklistItem updated) {
        ChecklistItem item = getById(id);
        item.setItemName(updated.getItemName());
        item.setItemDescription(updated.getItemDescription());
        item.setUnit(updated.getUnit());
        return repository.save(item);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
