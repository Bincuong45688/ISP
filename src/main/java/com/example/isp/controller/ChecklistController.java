package com.example.isp.controller;

import com.example.isp.dto.request.CreateChecklistRequest;
import com.example.isp.dto.request.UpdateChecklistRequest;
import com.example.isp.dto.response.ChecklistResponse;
import com.example.isp.dto.response.ChecklistResponseNew;
import com.example.isp.model.Checklist;
import com.example.isp.model.ChecklistItem;
import com.example.isp.model.Ritual;
import com.example.isp.service.ChecklistService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/checklists")
@RequiredArgsConstructor
public class ChecklistController {

    private final ChecklistService checklistService;

    // ==== List tất cả ====
    @GetMapping
    public List<ChecklistResponse> list() {
        return checklistService.list()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // ==== Get by ID ====
    @GetMapping("/{id}")
    public ChecklistResponse get(@PathVariable Long id) {
        return toResponse(checklistService.get(id));
    }

    // ==== Create ====
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ChecklistResponse create(@Valid @RequestBody CreateChecklistRequest request) {
        Checklist checklist = Checklist.builder()
                .ritual(Ritual.builder().ritualId(request.ritualId()).build())
                .item(ChecklistItem.builder().itemId(request.itemId()).build())
                .quantity(request.quantity())
                .checkNote(request.checkNote())
                .build();

        return toResponse(checklistService.create(checklist));
    }

    // ==== Update ====
    @PutMapping("/{id}")
    public ChecklistResponse update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateChecklistRequest request
    ) {
        Checklist patch = Checklist.builder()
                .ritual(request.ritualId() != null ? Ritual.builder().ritualId(request.ritualId()).build() : null)
                .item(request.itemId() != null ? ChecklistItem.builder().itemId(request.itemId()).build() : null)
                .quantity(request.quantity())
                .checkNote(request.checkNote())
                .build();

        return toResponse(checklistService.update(id, patch));
    }

    // ==== Delete ====
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        checklistService.delete(id);
    }

    // ==== Get by Ritual ID ====
    @GetMapping("/ritual/{ritualId}")
    public List<ChecklistResponse> getByRitualId(@PathVariable Long ritualId) {
        return checklistService.getByRitualId(ritualId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // ==== Get by Item ID ====
    @GetMapping("/item/{itemId}")
    public List<ChecklistResponse> getByItemId(@PathVariable Long itemId) {
        return checklistService.getByItemId(itemId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // ==== Filter theo ritual ID và item ID với phân trang ====
    // Gọi: /api/checklists/filter?ritualId=1&itemId=2&page=0&size=10&sort=checklistId,desc
    @GetMapping("/filter")
    public Page<ChecklistResponse> filter(
            @RequestParam(required = false) Long ritualId,
            @RequestParam(required = false) Long itemId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "checklistId") String sortBy,
            @RequestParam(defaultValue = "DESC") Sort.Direction direction
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        return checklistService.filter(ritualId, itemId, pageable)
                .map(this::toResponse);
    }

    // ==== Helper method để convert Entity -> Response ====
    private ChecklistResponse toResponse(Checklist c) {
        return new ChecklistResponse(
                c.getChecklistId(),
                c.getRitual() != null ? c.getRitual().getRitualId() : null,
                c.getRitual() != null ? c.getRitual().getRitualName() : null,
                c.getItem() != null ? c.getItem().getItemId() : null,
                c.getItem() != null ? c.getItem().getItemName() : null,
                c.getItem() != null ? c.getItem().getUnit() : null,
                c.getQuantity(),
                c.getCheckNote()
        );
    }
    // ==== Group checklist theo tên lễ hội (chỉ giữ vật phẩm, không có ritualId/ritualName) ====
    @GetMapping("/grouped")
    public Map<String, List<ChecklistResponseNew>> getGroupedByRitual() {
        List<Checklist> checklists = checklistService.list();

        // Gom nhóm theo tên lễ hội
        return checklists.stream()
                .collect(Collectors.groupingBy(
                        c -> c.getRitual() != null ? c.getRitual().getRitualName() : "Không xác định",
                        LinkedHashMap::new,
                        Collectors.mapping(this::toResponseNew, Collectors.toList())
                ));
    }

    // === Helper: map sang DTO mới (không chứa ritualId/ritualName) ===
    private ChecklistResponseNew toResponseNew(Checklist c) {
        return new ChecklistResponseNew(
                c.getChecklistId(),
                c.getItem() != null ? c.getItem().getItemId() : null,
                c.getItem() != null ? c.getItem().getItemName() : null,
                c.getItem() != null ? c.getItem().getUnit() : null,
                c.getQuantity(),
                c.getCheckNote()
        );
    }

}
