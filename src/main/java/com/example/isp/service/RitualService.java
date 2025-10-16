package com.example.isp.service;


import com.example.isp.model.enums.Ritual;

import java.util.List;

public interface RitualService {
    List<Ritual> getAllRituals();
    Ritual getRitualById(Long id);
    List<Ritual> getRitualsByRegion(String region);
    Ritual createRitual(Ritual ritual);
    Ritual updateRitual(Long id, Ritual ritual);
    void deleteRitual(Long id);
}