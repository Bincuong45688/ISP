package com.example.isp.controller;

import com.example.isp.dto.request.CreateChecklistItemRequest;
import com.example.isp.dto.request.UpdateChecklistItemRequest;
import com.example.isp.dto.response.ChecklistItemResponse;
import com.example.isp.model.ChecklistItem;
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
    public ChecklistItemResponse create(@Valid @RequestBody CreateChecklistItemRequest request) {
        ChecklistItem item = ChecklistItem.builder()
                .itemName(request.itemName())
                .unit(request.unit())
                .stockQuantity(request.stockQuantity() != null ? request.stockQuantity() : 0)
                .build();

        return toResponse(checklistItemService.create(item));
    }

    // ==== Update ====
    @PutMapping("/{id}")
    public ChecklistItemResponse update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateChecklistItemRequest request
    ) {
        ChecklistItem patch = ChecklistItem.builder()
                .itemName(request.itemName())
                .unit(request.unit())
                .stockQuantity(request.stockQuantity())
                .build();

        return toResponse(checklistItemService.update(id, patch));
    }

    // ==== Delete ====
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        checklistItemService.delete(id);
    }

    // ==== Search theo tên ====
    @GetMapping("/search")
    public List<ChecklistItemResponse> search(@RequestParam String q) {
        return checklistItemService.searchByName(q)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // ==== Filter theo tên với phân trang ====
    // Gọi: /api/checklist-items/filter?name=hương&page=0&size=10&sort=itemId,desc
    @GetMapping("/filter")
    public Page<ChecklistItemResponse> filter(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "itemId") String sortBy,
            @RequestParam(defaultValue = "DESC") Sort.Direction direction
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        return checklistItemService.filter(name, pageable)
                .map(this::toResponse);
    }

    // ==== Helper method để convert Entity -> Response ====
    private ChecklistItemResponse toResponse(ChecklistItem item) {
        return new ChecklistItemResponse(
                item.getItemId(),
                item.getItemName(),
                item.getUnit(),
                item.getStockQuantity()
        );
    }
}
