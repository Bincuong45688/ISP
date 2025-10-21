package com.example.isp.controller;

import com.example.isp.dto.request.CreateRegionRequest;
import com.example.isp.dto.response.RegionResponse;
import com.example.isp.model.Region;
import com.example.isp.service.RegionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/regions")
@RequiredArgsConstructor
public class RegionController {

    private final RegionService regionService;


    @GetMapping
    public List<RegionResponse> list() {
        return regionService.list()
                .stream()
                .map(this::toRes)
                .toList();
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RegionResponse create(@Valid @RequestBody CreateRegionRequest req) {
        Region region = new Region();
        region.setRegionName(req.regionName());
        region.setRegionDescription(req.regionDescription());

        Region saved = regionService.create(region);
        return toRes(saved);
    }


    @PutMapping("/{id}")
    public RegionResponse update(@PathVariable Long id,
                                 @Valid @RequestBody CreateRegionRequest req) {
        Region toUpdate = new Region();
        toUpdate.setRegionName(req.regionName());
        toUpdate.setRegionDescription(req.regionDescription());

        Region saved = regionService.update(id, toUpdate);
        return toRes(saved);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        regionService.delete(id);
    }


    private RegionResponse toRes(Region r) {
        return new RegionResponse(
                r.getRegionId(),
                r.getRegionName(),
                r.getRegionDescription()
        );
    }
}
