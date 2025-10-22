package com.example.isp.service;

import com.example.isp.model.ChecklistItem;
import com.example.isp.repository.ChecklistItemRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ChecklistItemServiceImpl implements ChecklistItemService {

    private final ChecklistItemRepository checklistItemRepository;

    @Override
    public ChecklistItem create(ChecklistItem item) {
        return checklistItemRepository.save(item);
    }

    @Override
    public ChecklistItem update(Long id, ChecklistItem item) {
        ChecklistItem existing = checklistItemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ChecklistItem not found: " + id));

        if (item.getItemName() != null) {
            existing.setItemName(item.getItemName());
        }
        if (item.getUnit() != null) {
            existing.setUnit(item.getUnit());
        }

        return checklistItemRepository.save(existing);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChecklistItem> list() {
        return checklistItemRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public ChecklistItem get(Long id) {
        return checklistItemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ChecklistItem not found: " + id));
    }

    @Override
    public void delete(Long id) {
        if (!checklistItemRepository.existsById(id)) {
            throw new EntityNotFoundException("ChecklistItem not found: " + id);
        }
        checklistItemRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChecklistItem> searchByName(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return checklistItemRepository.findAll();
        }
        return checklistItemRepository.searchByName(keyword);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ChecklistItem> filter(String name, Pageable pageable) {
        final String nameF = name;

        Specification<ChecklistItem> spec = Specification.allOf();

        // Lọc theo tên (tìm kiếm gần đúng)
        if (nameF != null && !nameF.isBlank()) {
            spec = spec.and((root, query, cb) ->
                    cb.like(cb.lower(root.get("itemName")),
                            "%" + nameF.toLowerCase() + "%"));
        }

        return checklistItemRepository.findAll(spec, pageable);
    }
}
