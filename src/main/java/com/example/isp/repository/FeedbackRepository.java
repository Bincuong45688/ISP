package com.example.isp.repository;

import com.example.isp.model.Feedback;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    // SEARCH + FILTER FEEDBACK
    @Query("""
        SELECT f FROM Feedback f 
        WHERE (:keyword IS NULL OR LOWER(f.content) LIKE LOWER(CONCAT('%', :keyword, '%')))
        AND (:star IS NULL OR f.star = :star)
    """)
    Page<Feedback> searchFeedbacks(String keyword, Integer star, Pageable pageable);

    // CHECK user đã review product chưa
    boolean existsByUserIdAndProductId(Long userId, Long productId);

    // LẤY feedback của 1 user + 1 product
    Optional<Feedback> findByProductIdAndUserId(Long productId, Long userId);

    // TẤT CẢ feedback của 1 product
    List<Feedback> findAllByProductId(Long productId);

    // TẤT CẢ feedback của 1 user
    List<Feedback> findAllByUserId(Long userId);
    List<Feedback> findAllByProductIdAndUserId(Long productId, Long userId);

}
