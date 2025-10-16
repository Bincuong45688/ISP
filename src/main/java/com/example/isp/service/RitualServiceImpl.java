package com.example.isp.service;


import com.example.isp.model.enums.Ritual;
import com.example.isp.repository.RitualRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RitualServiceImpl implements RitualService {

    private final RitualRepository ritualRepository;

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
    public List<Ritual> getRitualsByRegion(String region) {
        return ritualRepository.findByRegionIgnoreCase(region);
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
        existing.setRegion(ritual.getRegion());
        existing.setActive(ritual.isActive());
        return ritualRepository.save(existing);
    }

    @Override
    public void deleteRitual(Long id) {
        ritualRepository.deleteById(id);
    }
}