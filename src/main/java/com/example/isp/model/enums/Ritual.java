package com.example.isp.model.enums;

import com.example.isp.model.Region;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "rituals")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ritual {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String ritualName;

    @Column(length = 255)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id", nullable = false)
    private Region region;

    @Column(name = "date_lunar")
    private String dateLunar;   // ngày âm lịch (text)

    @Column(name = "date_solar")
    private LocalDate dateSolar;  // ngày dương lịch (DATE)

    @Column(name = "meaning", columnDefinition = "TEXT")
    private String meaning;

    @Column(nullable = false)
    private boolean active = true;

    @Column(name = "image_url")
    private String imageUrl;
}
