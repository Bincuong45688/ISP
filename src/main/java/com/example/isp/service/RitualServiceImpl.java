package com.example.isp.service;

import com.example.isp.model.Region;
import com.example.isp.model.enums.Ritual;
import com.example.isp.repository.RegionRepository;
import com.example.isp.repository.RitualRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RitualServiceImpl implements RitualService {

    private final RitualRepository ritualRepository;
    private final RegionRepository regionRepository;

    @Override
    public List<Ritual> getAllRituals() {
        return ritualRepository.findAll();
    }

    @Override
    public Ritual getRitualById(Long id) {
        return ritualRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ritual not found with id: " + id));
    }

    @Override
    public List<Ritual> getRitualsByRegion(String regionName) {
        return ritualRepository.findByRegion_RegionNameIgnoreCase(regionName);
    }

    @Override
    public Ritual createRitual(Ritual ritual) {
        return ritualRepository.save(ritual);
    }

    @Override
    public Ritual updateRitual(Long id, Ritual ritual) {
        Ritual existing = getRitualById(id);
        existing.setRitualName(ritual.getRitualName());
        existing.setDescription(ritual.getDescription());
        existing.setDateLunar(ritual.getDateLunar());
        existing.setDateSolar(ritual.getDateSolar());
        existing.setMeaning(ritual.getMeaning());
        existing.setRegion(ritual.getRegion());
        existing.setActive(ritual.isActive());
        existing.setImageUrl(ritual.getImageUrl());
        return ritualRepository.save(existing);
    }

    @Override
    public Ritual updateRitualImage(Long id, String imageUrl) {
        Ritual existing = getRitualById(id);
        existing.setImageUrl(imageUrl);
        return ritualRepository.save(existing);
    }

    @Override
    public void deleteRitual(Long id) {
        ritualRepository.deleteById(id);
    }
}
