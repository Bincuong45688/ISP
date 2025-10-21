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
    public ChecklistItem create(ChecklistItem checklistItem) {
        return checklistItemRepository.save(checklistItem);
    }

    @Override
    public ChecklistItem update(Long id, ChecklistItem checklistItem) {
        ChecklistItem existing = checklistItemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ChecklistItem not found: " + id));

        if (checklistItem.getRitual() != null) {
            existing.setRitual(checklistItem.getRitual());
        }
        if (checklistItem.getChecklist() != null) {
            existing.setChecklist(checklistItem.getChecklist());
        }
        if (checklistItem.getQuantity() != null) {
            existing.setQuantity(checklistItem.getQuantity());
        }
        if (checklistItem.getCheckNote() != null) {
            existing.setCheckNote(checklistItem.getCheckNote());
        }

        return checklistItemRepository.save(existing);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChecklistItem> list() {
        return checklistItemRepository.findAllWithRelations();
    }

    @Override
    @Transactional(readOnly = true)
    public ChecklistItem get(Long id) {
        return checklistItemRepository.findByIdWithRelations(id)
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
    public List<ChecklistItem> getByRitualId(Long ritualId) {
        return checklistItemRepository.findByRitualId(ritualId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChecklistItem> getByChecklistId(Long itemId) {
        return checklistItemRepository.findByChecklistId(itemId);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ChecklistItem> filter(Long ritualId, Long itemId, Pageable pageable) {
        final Long ritualIdF = ritualId;
        final Long itemIdF = itemId;

        Specification<ChecklistItem> spec = Specification.allOf();

        // Lọc theo ritual
        if (ritualIdF != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("ritual").get("ritualId"), ritualIdF));
        }

        // Lọc theo checklist item
        if (itemIdF != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("checklist").get("itemId"), itemIdF));
        }

        return checklistItemRepository.findAll(spec, pageable);
    }
}
