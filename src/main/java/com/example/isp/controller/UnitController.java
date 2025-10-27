package com.example.isp.controller;

import com.example.isp.model.enums.Unit;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/units")
public class UnitController {

    @GetMapping
    public List<Map<String, String>> getAllUnits() {
        return Arrays.stream(Unit.values())
                .map(unit -> Map.of(
                        "name", unit.name(),
                        "displayName", unit.getDisplayName()
                ))
                .collect(Collectors.toList());
    }
}
