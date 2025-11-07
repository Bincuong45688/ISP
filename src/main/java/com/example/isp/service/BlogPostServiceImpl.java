package com.example.isp.service;

import com.example.isp.model.BlogPost;
import com.example.isp.repository.BlogPostRepository;
import com.example.isp.service.BlogPostService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogPostServiceImpl implements BlogPostService {

    private final BlogPostRepository repository;

    private static final ZoneId VIETNAM_ZONE = ZoneId.of("Asia/Ho_Chi_Minh");

    @Override
    public List<BlogPost> list() {
        return repository.findAll();
    }

    @Override
    public BlogPost getBySlug(String slug) {
        return repository.findBySlug(slug)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy bài viết với slug: " + slug));
    }

    @Override
    public List<BlogPost> getByCategory(String category) {
        return repository.findByCategoryIgnoreCase(category);
    }

    @Override
    public BlogPost create(BlogPost post) {
        // 👉 Lấy giờ thực theo múi giờ Việt Nam
        post.setCreatedAt(LocalDateTime.now(VIETNAM_ZONE));
        post.setUpdatedAt(LocalDateTime.now(VIETNAM_ZONE));
        return repository.save(post);
    }

    @Override
    public BlogPost update(Long id, BlogPost patch) {
        BlogPost existing = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy bài viết với id: " + id));

        if (patch.getTitle() != null) existing.setTitle(patch.getTitle());
        if (patch.getSlug() != null) existing.setSlug(patch.getSlug());
        if (patch.getSummary() != null) existing.setSummary(patch.getSummary());
        if (patch.getContent() != null) existing.setContent(patch.getContent());
        if (patch.getCategory() != null) existing.setCategory(patch.getCategory());
        if (patch.getThumbnailUrl() != null) existing.setThumbnailUrl(patch.getThumbnailUrl());

        // 👉 Cập nhật lại giờ Việt Nam mỗi khi chỉnh sửa
        existing.setUpdatedAt(LocalDateTime.now(VIETNAM_ZONE));

        return repository.save(existing);
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Không tìm thấy bài viết với id: " + id);
        }
        repository.deleteById(id);
    }

    @Override
    public List<BlogPost> searchByTitle(String keyword) {
        return repository.searchByTitle(keyword);
    }

    @Override
    public Page<BlogPost> filter(String title, String category, Pageable pageable) {
        return repository.filter(title, category, pageable);
    }
}
