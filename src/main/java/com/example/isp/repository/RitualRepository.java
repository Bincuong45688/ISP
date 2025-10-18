package com.example.isp.repository;


import com.example.isp.model.enums.Ritual;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RitualRepository extends JpaRepository<Ritual, Long> {
    List<Ritual> findByRegionIgnoreCase(String region);
}