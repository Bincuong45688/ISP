package com.example.isp.controller;

import com.example.isp.dto.request.FeedbackDTO;
import com.example.isp.dto.response.FeedbackDTORespone;
import com.example.isp.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/feedbacks")
@RequiredArgsConstructor
public class FeedbackController {

    private final FeedbackService service;

    @GetMapping
    public ResponseEntity<Page<FeedbackDTORespone>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Integer star) {

        Page<FeedbackDTORespone> list = service.listAll(keyword, star, page, size);
        return ResponseEntity.ok(list);
    }

    @PostMapping
    public ResponseEntity<FeedbackDTORespone> create(@RequestBody FeedbackDTO dto) {
        FeedbackDTORespone saved = service.save(dto);
        return ResponseEntity.ok(saved);
    }
    // Cập nhật feedback
    @PutMapping("/{id}")
    public ResponseEntity<FeedbackDTORespone> update(
            @PathVariable Long id,
            @RequestBody FeedbackDTO dto) {
        FeedbackDTORespone updated = service.update(id, dto);
        return ResponseEntity.ok(updated);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok("Deleted feedback id = " + id);
    }
}




