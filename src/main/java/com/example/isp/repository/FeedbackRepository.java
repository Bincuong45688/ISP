package com.example.isp.repository;


import com.example.isp.model.Feedback;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import org.springframework.data.jpa.repository.Query;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    @Query("SELECT f FROM Feedback f WHERE " +
            "(:keyword IS NULL OR LOWER(f.content) LIKE LOWER(CONCAT('%', :keyword, '%'))) AND " +
            "(:star IS NULL OR f.star = :star)")
    Page<Feedback> searchFeedbacks(String keyword, Integer star, Pageable pageable);
}



