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
        Checklist saved = checklistRepository.save(checklist);
        // Reload to ensure relations are loaded
        return checklistRepository.findByIdWithRelations(saved.getChecklistId())
                .orElseThrow(() -> new EntityNotFoundException("Checklist not found after save: " + saved.getChecklistId()));
    }

    @Override
    public Checklist update(Long id, Checklist checklist) {
        Checklist existing = checklistRepository.findByIdWithRelations(id)
                .orElseThrow(() -> new EntityNotFoundException("Checklist not found: " + id));

        if (checklist.getRitual() != null) {
            existing.setRitual(checklist.getRitual());
        }
        if (checklist.getItem() != null) {
            existing.setItem(checklist.getItem());
        }
        if (checklist.getQuantity() != null) {
            existing.setQuantity(checklist.getQuantity());
        }
        if (checklist.getCheckNote() != null) {
            existing.setCheckNote(checklist.getCheckNote());
        }

        Checklist saved = checklistRepository.save(existing);
        // Reload to ensure relations are loaded
        return checklistRepository.findByIdWithRelations(saved.getChecklistId())
                .orElseThrow(() -> new EntityNotFoundException("Checklist not found after save: " + saved.getChecklistId()));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Checklist> list() {
        return checklistRepository.findAllWithRelations();
    }

    @Override
    @Transactional(readOnly = true)
    public Checklist get(Long id) {
        return checklistRepository.findByIdWithRelations(id)
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
    public List<Checklist> getByRitualId(Long ritualId) {
        return checklistRepository.findByRitualId(ritualId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Checklist> getByItemId(Long itemId) {
        return checklistRepository.findByItemId(itemId);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Checklist> filter(Long ritualId, Long itemId, Pageable pageable) {
        final Long ritualIdF = ritualId;
        final Long itemIdF = itemId;

        Specification<Checklist> spec = Specification.allOf();

        // Lọc theo ritual ID
        if (ritualIdF != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("ritual").get("ritualId"), ritualIdF));
        }

        // Lọc theo item ID
        if (itemIdF != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("item").get("itemId"), itemIdF));
        }

        return checklistRepository.findAll(spec, pageable);
    }
}
