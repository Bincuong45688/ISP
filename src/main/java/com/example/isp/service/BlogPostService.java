package com.example.isp.service;

import com.example.isp.model.BlogPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BlogPostService {

    List<BlogPost> list();

    BlogPost getBySlug(String slug);

    List<BlogPost> getByCategory(String category);

    BlogPost create(BlogPost post);

    BlogPost update(Long id, BlogPost patch);

    void delete(Long id);

    List<BlogPost> searchByTitle(String keyword);

    Page<BlogPost> filter(String title, String category, Pageable pageable);
}
