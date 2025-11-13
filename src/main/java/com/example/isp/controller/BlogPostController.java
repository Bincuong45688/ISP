package com.example.isp.controller;

import com.example.isp.dto.response.BlogPostResponse;
import com.example.isp.model.BlogPost;
import com.example.isp.service.BlogPostService;
import com.example.isp.service.CloudinaryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@RestController
@RequestMapping("/api/v1/blogs")
@RequiredArgsConstructor
public class BlogPostController {

    private final BlogPostService blogService;
    private final CloudinaryService cloudinaryService;

    // ==== Get all blogs ====
    @GetMapping
    @Operation(summary = "Lấy danh sách bài viết blog")
    public List<BlogPostResponse> list() {
        return blogService.list().stream()
                .map(this::toResponse)
                .toList();
    }

    // ==== Get by slug ====
    @GetMapping("/{slug}")
    @Operation(summary = "Lấy chi tiết bài viết theo slug")
    public BlogPostResponse getBySlug(@PathVariable String slug) {
        return toResponse(blogService.getBySlug(slug));
    }

    // ==== Filter by category ====
    @GetMapping("/category/{category}")
    @Operation(summary = "Lọc bài viết theo category (VD: Hướng dẫn, Tâm linh, Tin tức)")
    public List<BlogPostResponse> getByCategory(@PathVariable String category) {
        return blogService.getByCategory(category)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // ==== Create (multipart/form-data) ====
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Tạo bài viết blog mới (author mặc định 'Staff', thời gian theo giờ VN)")
    public BlogPostResponse create(
            @RequestParam String title,
            @RequestParam(required = false) String slug,
            @RequestParam(required = false) String summary,
            @RequestParam(required = false) String content,
            @RequestParam(required = false) String category,
            @RequestParam(value = "file", required = false) MultipartFile file
    ) {
        String imageUrl = null;
        if (file != null && !file.isEmpty()) {
            imageUrl = cloudinaryService.uploadImage(file, "isp/blogs");
        }

        BlogPost post = BlogPost.builder()
                .title(title)
                .slug(slug)
                .summary(summary)
                .content(content)
                .category(category)
                .thumbnailUrl(imageUrl)
                .authorName("Staff") // ✅ Không còn dùng Staff entity
                .createdAt(LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh")))
                .build();

        return toResponse(blogService.create(post));
    }

    // ==== Update (multipart/form-data) ====
    @PutMapping(path = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Cập nhật bài viết blog (cho phép đổi ảnh)")
    public BlogPostResponse update(
            @PathVariable Long id,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String slug,
            @RequestParam(required = false) String summary,
            @RequestParam(required = false) String content,
            @RequestParam(required = false) String category,
            @RequestParam(value = "file", required = false) MultipartFile file
    ) {
        BlogPost patch = BlogPost.builder()
                .title(title)
                .slug(slug)
                .summary(summary)
                .content(content)
                .category(category)
                .build();

        if (file != null && !file.isEmpty()) {
            String newUrl = cloudinaryService.uploadImage(file, "isp/blogs");
            patch.setThumbnailUrl(newUrl);
        }

        return toResponse(blogService.update(id, patch));
    }

    // ==== Delete ====
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Xóa bài viết blog")
    public void delete(@PathVariable Long id) {
        blogService.delete(id);
    }

    // ==== Search ====
    @GetMapping("/search")
    @Operation(summary = "Tìm kiếm bài viết theo tiêu đề")
    public List<BlogPostResponse> search(@RequestParam String q) {
        return blogService.searchByTitle(q)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // ==== Filter with pagination ====
    // Gọi: /api/v1/blogs/filter?title=chùa&category=Tâm linh&page=0&size=10&sort=id,desc
    @GetMapping("/filter")
    @Operation(summary = "Lọc bài viết theo tiêu đề, category (phân trang)")
    public Page<BlogPostResponse> filter(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "DESC") Sort.Direction direction
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        return blogService.filter(title, category, pageable)
                .map(this::toResponse);
    }

    // ==== Helper mapper ====
    private BlogPostResponse toResponse(BlogPost post) {
        return new BlogPostResponse(
                post.getId(),
                post.getTitle(),
                post.getSlug(),
                post.getSummary(),
                post.getContent(),
                post.getCategory(),
                post.getThumbnailUrl(),
                post.getCreatedAt(),
                post.getUpdatedAt(),
                post.getAuthorName() // ✅ Dùng authorName thay cho Staff
        );
    }
}
