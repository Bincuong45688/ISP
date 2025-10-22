package com.example.isp.service;

import com.example.isp.model.Checklist;
import com.example.isp.repository.ChecklistRepository;
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
public class ChecklistServiceImpl implements ChecklistService {

    private final ChecklistRepository checklistRepository;

    @Override
    public Checklist create(Checklist checklist) {
        // Kiểm tra tên đã tồn tại
        if (checklistRepository.existsByItemName(checklist.getItemName())) {
            throw new IllegalArgumentException("Tên vật phẩm đã tồn tại: " + checklist.getItemName());
        }
        return checklistRepository.save(checklist);
    }

    @Override
    public Checklist update(Long id, Checklist checklist) {
        Checklist existing = checklistRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Checklist not found: " + id));

        if (checklist.getItemName() != null) {
            // Kiểm tra tên mới có trùng với item khác không
            if (!existing.getItemName().equals(checklist.getItemName()) 
                && checklistRepository.existsByItemName(checklist.getItemName())) {
                throw new IllegalArgumentException("Tên vật phẩm đã tồn tại: " + checklist.getItemName());
            }
            existing.setItemName(checklist.getItemName());
        }
        if (checklist.getItemDescription() != null) {
            existing.setItemDescription(checklist.getItemDescription());
        }
        if (checklist.getUnit() != null) {
            existing.setUnit(checklist.getUnit());
        }

        return checklistRepository.save(existing);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Checklist> list() {
        return checklistRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Checklist get(Long id) {
        return checklistRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Checklist not found: " + id));
    }

    @Override
    public void delete(Long id) {
        if (!checklistRepository.existsById(id)) {
            throw new EntityNotFoundException("Checklist not found: " + id);
        }
        checklistRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Checklist> searchByName(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return checklistRepository.findAll();
        }
        return checklistRepository.searchByName(keyword);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Checklist> filter(String name, Pageable pageable) {
        final String nameF = name;

        Specification<Checklist> spec = Specification.allOf();

        // Lọc theo tên (tìm kiếm gần đúng)
        if (nameF != null && !nameF.isBlank()) {
            spec = spec.and((root, query, cb) ->
                    cb.like(cb.lower(root.get("itemName")),
                            "%" + nameF.toLowerCase() + "%"));
        }

        return checklistRepository.findAll(spec, pageable);
    }
}
