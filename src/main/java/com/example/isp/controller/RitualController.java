package com.example.isp.controller;

import com.example.isp.dto.request.CreateRitualRequest;
import com.example.isp.dto.request.UpdateRitualRequest;
import com.example.isp.dto.response.RitualResponse;
import com.example.isp.model.Region;
import com.example.isp.model.Ritual;
import com.example.isp.service.CloudinaryService;
import com.example.isp.service.RitualService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/rituals")
@RequiredArgsConstructor
public class RitualController {

    private final RitualService ritualService;
    private final CloudinaryService cloudinaryService;

    // ==== List tất cả ====
    @GetMapping
    public List<RitualResponse> list() {
        return ritualService.list()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // ==== Get by ID ====
    @GetMapping("/{id}")
    public RitualResponse get(@PathVariable Long id) {
        return toResponse(ritualService.get(id));
    }

    // ==== Create (JSON) ====
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RitualResponse create(@Valid @RequestBody CreateRitualRequest req) {
        Ritual ritual = Ritual.builder()
                .ritualName(req.ritualName())
                .dateLunar(req.dateLunar())
                .region(Region.builder().regionId(req.regionId()).build())
                .dateSolar(req.dateSolar())
                .description(req.description())
                .meaning(req.meaning())
                .build();

        return toResponse(ritualService.create(ritual));
    }

    // ==== Create with Image (multipart/form-data) ====
    @PostMapping(path = "/with-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public RitualResponse createWithImage(
            @RequestParam String ritualName,
            @RequestParam(required = false) String dateLunar,
            @RequestParam Long regionId,
            @RequestParam(required = false) LocalDate dateSolar,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) String meaning,
            @RequestParam(value = "file") MultipartFile file
    ) {
        String imageUrl = cloudinaryService.uploadImage(file, "isp/rituals");

        Ritual ritual = Ritual.builder()
                .ritualName(ritualName)
                .dateLunar(dateLunar)
                .region(Region.builder().regionId(regionId).build())
                .dateSolar(dateSolar)
                .description(description)
                .meaning(meaning)
                .imageUrl(imageUrl)
                .build();

        return toResponse(ritualService.create(ritual));
    }

    // ==== Update (JSON) ====
    @PutMapping("/{id}")
    public RitualResponse update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateRitualRequest req) {
        
        Ritual patch = Ritual.builder()
                .ritualName(req.ritualName())
                .dateLunar(req.dateLunar())
                .region(req.regionId() != null ? Region.builder().regionId(req.regionId()).build() : null)
                .dateSolar(req.dateSolar())
                .description(req.description())
                .meaning(req.meaning())
                .build();

        return toResponse(ritualService.update(id, patch));
    }

    // ==== Update Image ====
    @PutMapping(path = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public RitualResponse updateImage(
            @PathVariable Long id,
            @RequestParam(value = "file") MultipartFile file
    ) {
        String newUrl = cloudinaryService.uploadImage(file, "isp/rituals");
        Ritual patch = Ritual.builder()
                .imageUrl(newUrl)
                .build();
        return toResponse(ritualService.update(id, patch));
    }

    // ==== Delete ====
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        ritualService.delete(id);
    }

    // ==== Search theo tên ====
    @GetMapping("/search")
    public List<RitualResponse> search(@RequestParam String q) {
        return ritualService.searchByName(q)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // ==== Filter theo tên và vùng miền với phân trang ====
    // Gọi: /api/rituals/filter?name=tết&regionId=1&page=0&size=10&sort=ritualId,desc
    @GetMapping("/filter")
    public Page<RitualResponse> filter(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long regionId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "ritualId") String sortBy,
            @RequestParam(defaultValue = "DESC") Sort.Direction direction
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        return ritualService.filter(name, regionId, pageable)
                .map(this::toResponse);
    }

    // ==== Helper method để convert Entity -> Response ====
    private RitualResponse toResponse(Ritual r) {
        return new RitualResponse(
                r.getRitualId(),
                r.getRitualName(),
                r.getDateLunar(),
                r.getRegion() != null ? r.getRegion().getRegionId() : null,
                r.getRegion() != null ? r.getRegion().getRegionName() : null,
                r.getDateSolar(),
                r.getDescription(),
                r.getMeaning(),
                r.getImageUrl()
        );
    }
}
