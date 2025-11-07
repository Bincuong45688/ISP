package com.example.isp.repository;

import com.example.isp.model.BlogPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BlogPostRepository extends JpaRepository<BlogPost, Long> {

    Optional<BlogPost> findBySlug(String slug);

    List<BlogPost> findByCategoryIgnoreCase(String category);

    @Query("SELECT b FROM BlogPost b WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<BlogPost> searchByTitle(String keyword);

    @Query("""
        SELECT b FROM BlogPost b
        WHERE (:title IS NULL OR LOWER(b.title) LIKE LOWER(CONCAT('%', :title, '%')))
          AND (:category IS NULL OR LOWER(b.category) = LOWER(:category))
    """)
    Page<BlogPost> filter(String title, String category, Pageable pageable);
}
