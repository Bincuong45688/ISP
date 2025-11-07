package com.example.isp.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record BlogPostResponse(
        Long id,
        String title,
        String slug,
        String summary,
        String content,
        String category,
        String thumbnailUrl,

        // ✅ Giờ tạo định dạng chuẩn Việt Nam
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm", timezone = "Asia/Ho_Chi_Minh")
        LocalDateTime createdAt,

        // ✅ Giờ cập nhật (nếu có)
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm", timezone = "Asia/Ho_Chi_Minh")
        LocalDateTime updatedAt,

        String authorName
) {}
