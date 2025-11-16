package com.example.isp.service;

import com.example.isp.dto.request.FeedbackUploadRequest;
import com.example.isp.dto.response.FeedbackDTORespone;
import com.example.isp.model.Feedback;
import com.example.isp.model.Order;
import com.example.isp.model.enums.OrderStatus;
import com.example.isp.repository.FeedbackRepository;
import com.example.isp.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final FeedbackRepository feedbackRepo;
    private final OrderRepository orderRepo;
    private final CloudinaryService cloudinaryService;

    // ===========================
    // GET ALL (Paging, Search)
    // ===========================
    public Page<FeedbackDTORespone> listAll(String keyword, Integer star, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Feedback> fbPage = feedbackRepo.searchFeedbacks(keyword, star, pageable);
        return fbPage.map(this::toResponse);
    }

    // ===========================
    // CHECK quyền feedback
    // ===========================
    public Object checkFeedback(Long userId, Long productId) {

        if (feedbackRepo.existsByUserIdAndProductId(userId, productId)) {
            return "reviewed";
        }

        boolean purchased = orderRepo.findAll().stream()
                .filter(o -> o.getCustomer().getCustomerId().equals(userId))
                .filter(o -> o.getStatus() == OrderStatus.COMPLETED)
                .anyMatch(o ->
                        o.getOrderDetails().stream()
                                .anyMatch(d -> d.getProduct().getProductId().equals(productId))
                );

        if (!purchased) return "notPurchased";

        return "allowed";
    }

    // ===========================
    // CREATE FEEDBACK (ONLY IMAGES)
    // ===========================
    public FeedbackDTORespone createWithFiles(FeedbackUploadRequest req) {

        // Fix empty images
        if (req.getImages() != null &&
                req.getImages().length == 1 &&
                (req.getImages()[0] == null || req.getImages()[0].getOriginalFilename().isEmpty())) {
            req.setImages(null);
        }

        // ⭐⭐⭐ LOAD ORDER WITH orderDetails (FIX LAZY)
        Order order = orderRepo.findByIdWithDetails(req.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // Check ownership
        if (!order.getCustomer().getCustomerId().equals(req.getUserId())) {
            throw new RuntimeException("Order does not belong to this user");
        }

        // Check status
        if (order.getStatus() != OrderStatus.COMPLETED) {
            throw new RuntimeException("Order not completed yet — cannot review");
        }

        // Check product purchased
        boolean containsProduct = order.getOrderDetails().stream()
                .anyMatch(d -> d.getProduct().getProductId().equals(req.getProductId()));

        if (!containsProduct) {
            throw new RuntimeException("User did not buy this product — cannot review");
        }

        // Check reviewed
        if (feedbackRepo.existsByUserIdAndProductId(req.getUserId(), req.getProductId())) {
            throw new RuntimeException("User already reviewed this product");
        }

        // Upload images
        StringBuilder imageUrls = new StringBuilder();
        if (req.getImages() != null) {
            for (MultipartFile img : req.getImages()) {
                String url = cloudinaryService.uploadImage(img, "isp/feedback/images");
                imageUrls.append(url).append(",");
            }
        }

        // Save
        Feedback fb = Feedback.builder()
                .orderId(req.getOrderId())
                .userId(req.getUserId())
                .productId(req.getProductId())
                .content(req.getContent())
                .star(req.getStar())
                .imageUrls(imageUrls.toString())
                .createdAt(LocalDateTime.now())
                .build();

        Feedback saved = feedbackRepo.save(fb);
        return toResponse(saved);
    }

    // ===========================
    // UPDATE FEEDBACK
    // ===========================
    public FeedbackDTORespone update(Long id, FeedbackUploadRequest req) {

        if (req.getImages() != null &&
                req.getImages().length == 1 &&
                (req.getImages()[0] == null || req.getImages()[0].getOriginalFilename().isEmpty())) {
            req.setImages(null);
        }

        Feedback fb = feedbackRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Feedback not found"));

        fb.setContent(req.getContent());
        fb.setStar(req.getStar());
        fb.setCreatedAt(LocalDateTime.now());

        if (req.getImages() != null) {
            StringBuilder img = new StringBuilder();
            for (MultipartFile file : req.getImages()) {
                img.append(cloudinaryService.uploadImage(file, "isp/feedback/images")).append(",");
            }
            fb.setImageUrls(img.toString());
        }

        feedbackRepo.save(fb);
        return toResponse(fb);
    }

    // ===========================
    // DELETE
    // ===========================
    public void delete(Long id) {
        feedbackRepo.deleteById(id);
    }

    // ===========================
    // ENTITY → DTO
    // ===========================
    private FeedbackDTORespone toResponse(Feedback f) {
        return FeedbackDTORespone.builder()
                .fbId(f.getFbId())
                .orderId(f.getOrderId())
                .productId(f.getProductId())
                .userId(f.getUserId())
                .userName("User " + f.getUserId())
                .content(f.getContent())
                .star(f.getStar())
                .createdAt(f.getCreatedAt())
                .imageUrls(f.getImageUrls())
                .build();
    }
    public List<FeedbackDTORespone> getByProductId(Long productId) {
        List<Feedback> list = feedbackRepo.findAllByProductId(productId);
        return list.stream()
                .map(this::toResponse)
                .toList();
    }
    public List<FeedbackDTORespone> getByUserId(Long userId) {
        List<Feedback> list = feedbackRepo.findAllByUserId(userId);
        return list.stream()
                .map(this::toResponse)
                .toList();
    }
    public FeedbackDTORespone getByUserAndProduct(Long userId, Long productId) {
        Feedback fb = feedbackRepo.findByProductIdAndUserId(productId, userId)
                .orElseThrow(() -> new RuntimeException("Feedback not found"));

        return toResponse(fb);
    }


}
