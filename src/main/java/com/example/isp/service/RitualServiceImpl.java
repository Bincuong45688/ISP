package com.example.isp.service;

import com.example.isp.model.Ritual;
import com.example.isp.repository.RitualRepository;
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
public class RitualServiceImpl implements RitualService {

    private final RitualRepository ritualRepository;

    @Override
    public Ritual create(Ritual ritual) {
        Ritual saved = ritualRepository.save(ritual);
        // Reload to ensure region is loaded
        return ritualRepository.findByIdWithRegion(saved.getRitualId())
                .orElseThrow(() -> new EntityNotFoundException("Ritual not found after save: " + saved.getRitualId()));
    }

    @Override
    public Ritual update(Long id, Ritual ritual) {
        Ritual existing = ritualRepository.findByIdWithRegion(id)
                .orElseThrow(() -> new EntityNotFoundException("Ritual not found: " + id));

        if (ritual.getRitualName() != null) {
            existing.setRitualName(ritual.getRitualName());
        }
        if (ritual.getDateLunar() != null) {
            existing.setDateLunar(ritual.getDateLunar());
        }
        if (ritual.getRegion() != null) {
            existing.setRegion(ritual.getRegion());
        }
        if (ritual.getDateSolar() != null) {
            existing.setDateSolar(ritual.getDateSolar());
        }
        if (ritual.getDescription() != null) {
            existing.setDescription(ritual.getDescription());
        }
        if (ritual.getMeaning() != null) {
            existing.setMeaning(ritual.getMeaning());
        }
        if (ritual.getImageUrl() != null) {
            existing.setImageUrl(ritual.getImageUrl());
        }

        Ritual saved = ritualRepository.save(existing);
        // Reload to ensure region is loaded
        return ritualRepository.findByIdWithRegion(saved.getRitualId())
                .orElseThrow(() -> new EntityNotFoundException("Ritual not found after save: " + saved.getRitualId()));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Ritual> list() {
        return ritualRepository.findAllWithRegion();
    }

    @Override
    @Transactional(readOnly = true)
    public Ritual get(Long id) {
        return ritualRepository.findByIdWithRegion(id)
                .orElseThrow(() -> new EntityNotFoundException("Ritual not found: " + id));
    }

    @Override
    public void delete(Long id) {
        if (!ritualRepository.existsById(id)) {
            throw new EntityNotFoundException("Ritual not found: " + id);
        }
        ritualRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Ritual> searchByName(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return ritualRepository.findAllWithRegion();
        }
        return ritualRepository.searchByName(keyword);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Ritual> filter(String name, Long regionId, Pageable pageable) {
        // Tạo biến final để dùng trong lambda Specification
        final String nameF = name;
        final Long regionIdF = regionId;

        Specification<Ritual> spec = Specification.allOf();

        // Lọc theo tên (tìm kiếm gần đúng)
        if (nameF != null && !nameF.isBlank()) {
            spec = spec.and((root, query, cb) ->
                    cb.like(cb.lower(root.get("ritualName")),
                            "%" + nameF.toLowerCase() + "%"));
        }

        // Lọc theo vùng miền
        if (regionIdF != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("region").get("regionId"), regionIdF));
        }

        return ritualRepository.findAll(spec, pageable);
    }
}
