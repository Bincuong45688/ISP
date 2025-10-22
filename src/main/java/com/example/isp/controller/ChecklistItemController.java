package com.example.isp.controller;

import com.example.isp.dto.request.CreateChecklistItemRequest;
import com.example.isp.dto.request.UpdateChecklistItemRequest;
import com.example.isp.dto.response.ChecklistItemResponse;
import com.example.isp.model.Checklist;
import com.example.isp.model.ChecklistItem;
import com.example.isp.model.Ritual;
import com.example.isp.service.ChecklistItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/checklist-items")
@RequiredArgsConstructor
public class ChecklistItemController {

    private final ChecklistItemService checklistItemService;

    // ==== List tất cả ====
    @GetMapping
    public List<ChecklistItemResponse> list() {
        return checklistItemService.list()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // ==== Get by ID ====
    @GetMapping("/{id}")
    public ChecklistItemResponse get(@PathVariable Long id) {
        return toResponse(checklistItemService.get(id));
    }

    // ==== Create ====
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ChecklistItemResponse create(@Valid @RequestBody CreateChecklistItemRequest req) {
        ChecklistItem item = ChecklistItem.builder()
                .ritual(Ritual.builder().ritualId(req.ritualId()).build())
                .checklist(Checklist.builder().itemId(req.itemId()).build())
                .quantity(req.quantity())
                .checkNote(req.checkNote())
                .build();

        return toResponse(checklistItemService.create(item));
    }

    // ==== Update ====
    @PutMapping("/{id}")
    public ChecklistItemResponse update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateChecklistItemRequest req) {
        
        ChecklistItem patch = ChecklistItem.builder()
                .ritual(req.ritualId() != null ? Ritual.builder().ritualId(req.ritualId()).build() : null)
                .checklist(req.itemId() != null ? Checklist.builder().itemId(req.itemId()).build() : null)
                .quantity(req.quantity())
                .checkNote(req.checkNote())
                .build();

        return toResponse(checklistItemService.update(id, patch));
    }

    // ==== Delete ====
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        checklistItemService.delete(id);
    }

    // ==== Get by Ritual ID ====
    @GetMapping("/by-ritual/{ritualId}")
    public List<ChecklistItemResponse> getByRitualId(@PathVariable Long ritualId) {
        return checklistItemService.getByRitualId(ritualId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // ==== Get by Checklist ID ====
    @GetMapping("/by-checklist/{itemId}")
    public List<ChecklistItemResponse> getByChecklistId(@PathVariable Long itemId) {
        return checklistItemService.getByChecklistId(itemId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // ==== Filter theo ritual và checklist với phân trang ====
    // Gọi: /api/checklist-items/filter?ritualId=1&itemId=2&page=0&size=10&sort=checklistId,desc
    @GetMapping("/filter")
    public Page<ChecklistItemResponse> filter(
            @RequestParam(required = false) Long ritualId,
            @RequestParam(required = false) Long itemId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "checklistId") String sortBy,
            @RequestParam(defaultValue = "DESC") Sort.Direction direction
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        return checklistItemService.filter(ritualId, itemId, pageable)
                .map(this::toResponse);
    }

    // ==== Helper method ====
    private ChecklistItemResponse toResponse(ChecklistItem ci) {
        return new ChecklistItemResponse(
                ci.getChecklistId(),
                ci.getRitual() != null ? ci.getRitual().getRitualId() : null,
                ci.getRitual() != null ? ci.getRitual().getRitualName() : null,
                ci.getChecklist() != null ? ci.getChecklist().getItemId() : null,
                ci.getChecklist() != null ? ci.getChecklist().getItemName() : null,
                ci.getQuantity(),
                ci.getCheckNote()
        );
    }
}
