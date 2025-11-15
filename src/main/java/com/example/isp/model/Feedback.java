package com.example.isp.model;


import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "feedbacks")
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fbId;

    private Long orderId;
    private Long userId;

    @Column(length = 1000)
    private String content;

    private int star;

    String imageUrl;
    @Column(length = 1000)
    private String imageUrls; // Lưu nhiều URL hình ảnh, cách nhau bằng dấu phẩy
    
    String videoUrl;
    @Column(length = 1000)
    private String videoUrls; // Lưu nhiều URL video, cách nhau bằng dấu phẩy

    private LocalDateTime createdAt = LocalDateTime.now();
}

