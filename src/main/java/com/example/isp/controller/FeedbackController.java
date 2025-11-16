package com.example.isp.controller;

import com.example.isp.dto.request.FeedbackUploadRequest;
import com.example.isp.dto.response.FeedbackDTORespone;
import com.example.isp.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/feedbacks")
@RequiredArgsConstructor
public class FeedbackController {

    private final FeedbackService service;

    /**
     * GET: Paging + search keyword + filter sao
     */
    @GetMapping
    public ResponseEntity<Page<FeedbackDTORespone>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Integer star) {

        return ResponseEntity.ok(service.listAll(keyword, star, page, size));
    }

    /**
     * CHECK quyền user có được phép feedback hay không
     */
    @GetMapping("/check")
    public ResponseEntity<?> checkFeedback(
            @RequestParam Long userId,
            @RequestParam Long productId) {

        return ResponseEntity.ok(service.checkFeedback(userId, productId));
    }

    /**
     * CREATE feedback + upload ảnh
     * => FE gửi multipart/form-data
     * => request = @ModelAttribute FeedbackUploadRequest
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FeedbackDTORespone> create(
            @ModelAttribute FeedbackUploadRequest request) {

        return ResponseEntity.ok(service.createWithFiles(request));
    }

    /**
     * UPDATE feedback + upload ảnh mới (optional)
     */
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FeedbackDTORespone> update(
            @PathVariable Long id,
            @ModelAttribute FeedbackUploadRequest request) {

        return ResponseEntity.ok(service.update(id, request));
    }

    /**
     * DELETE Feedback
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok("Deleted feedback id = " + id);
    }
    @GetMapping("/product/{productId}")
    public ResponseEntity<?> getByProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(service.getByProductId(productId));
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(service.getByUserId(userId));
    }
    @GetMapping("/user-product")
    public ResponseEntity<?> getByUserAndProduct(
            @RequestParam Long userId,
            @RequestParam Long productId
    ) {
        return ResponseEntity.ok(service.getByUserAndProduct(userId, productId));
    }

}
