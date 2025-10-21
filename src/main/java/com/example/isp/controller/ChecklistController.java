package com.example.isp.controller;

import com.example.isp.dto.request.CreateChecklistRequest;
import com.example.isp.dto.request.UpdateChecklistRequest;
import com.example.isp.dto.response.ChecklistResponse;
import com.example.isp.model.Checklist;
import com.example.isp.service.ChecklistService;
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
    public ChecklistResponse create(@Valid @RequestBody CreateChecklistRequest req) {
        Checklist checklist = Checklist.builder()
                .itemName(req.itemName())
                .itemDescription(req.itemDescription())
                .unit(req.unit())
                .build();

        return toResponse(checklistService.create(checklist));
    }

    // ==== Update ====
    @PutMapping("/{id}")
    public ChecklistResponse update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateChecklistRequest req) {
        
        Checklist patch = Checklist.builder()
                .itemName(req.itemName())
                .itemDescription(req.itemDescription())
                .unit(req.unit())
                .build();

        return toResponse(checklistService.update(id, patch));
    }

    // ==== Delete ====
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        checklistService.delete(id);
    }

    // ==== Search theo tên ====
    @GetMapping("/search")
    public List<ChecklistResponse> search(@RequestParam String q) {
        return checklistService.searchByName(q)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // ==== Filter theo tên với phân trang ====
    // Gọi: /api/checklists/filter?name=hương&page=0&size=10&sort=itemId,desc
    @GetMapping("/filter")
    public Page<ChecklistResponse> filter(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "itemId") String sortBy,
            @RequestParam(defaultValue = "DESC") Sort.Direction direction
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        return checklistService.filter(name, pageable)
                .map(this::toResponse);
    }

    // ==== Helper method ====
    private ChecklistResponse toResponse(Checklist c) {
        return new ChecklistResponse(
                c.getItemId(),
                c.getItemName(),
                c.getItemDescription(),
                c.getUnit()
        );
    }
}
