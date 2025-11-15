package com.example.isp.service;
import com.example.isp.dto.request.FeedbackDTO;
import com.example.isp.dto.response.FeedbackDTORespone;
import com.example.isp.model.Feedback;
import com.example.isp.repository.AccountRepository;
import com.example.isp.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final FeedbackRepository repo;

    public Page<FeedbackDTORespone> listAll(String keyword, Integer star, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Feedback> fbPage = repo.searchFeedbacks(keyword, star, pageable);

        return fbPage.map(f -> new FeedbackDTORespone(
                f.getFbId(),
                f.getOrderId(),
                "User " + f.getUserId(), // Sau có thể join sang User table
                f.getContent(),
                f.getStar(),
                f.getCreatedAt(),
                f.getImageUrls(),
                f.getVideoUrls()
        ));
    }

    public FeedbackDTORespone save(FeedbackDTO dto) {
        Feedback fb = new Feedback();
        BeanUtils.copyProperties(dto, fb);
        fb.setCreatedAt(LocalDateTime.now());
        Feedback saved = repo.save(fb);

        return new FeedbackDTORespone(
                saved.getFbId(),
                saved.getOrderId(),
                "User " + saved.getUserId(),
                saved.getContent(),
                saved.getStar(),
                saved.getCreatedAt(),
                saved.getImageUrls(),
                saved.getVideoUrls()
        );
    }

    public FeedbackDTORespone update(Long id, FeedbackDTO dto) {
        Feedback fb = repo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Feedback not found with id = " + id));

        fb.setOrderId(dto.getOrderId());
        fb.setContent(dto.getContent());
        fb.setStar(dto.getStar());
        fb.setCreatedAt(LocalDateTime.now());

        Feedback updated = repo.save(fb);

        return new FeedbackDTORespone(
                updated.getFbId(),
                updated.getOrderId(),
                "User " + updated.getUserId(),
                updated.getContent(),
                updated.getStar(),
                updated.getCreatedAt(),
                updated.getImageUrls(),
                updated.getVideoUrls()
        );
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}





