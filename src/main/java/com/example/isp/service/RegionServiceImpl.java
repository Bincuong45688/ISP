package com.example.isp.service;

import com.example.isp.model.Region;
import com.example.isp.repository.RegionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RegionServiceImpl implements RegionService {

    private final RegionRepository regionRepository;

    @Override
    public Region create(Region r) {
        return regionRepository.save(r);
    }

    @Override
    public Region update(Long id, Region u) {
        Region existing = regionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Region not found: " + id));

        existing.setRegionName(u.getRegionName());
        existing.setRegionDescription(u.getRegionDescription());


        return regionRepository.save(existing);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Region> list() {
        return regionRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        if (!regionRepository.existsById(id)) {
            throw new EntityNotFoundException("Region not found: " + id);
        }
        regionRepository.deleteById(id);
    }
}
