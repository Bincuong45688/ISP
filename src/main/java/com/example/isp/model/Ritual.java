package com.example.isp.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "Ritual")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Ritual {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ritual_id")
    private Long ritualId;

    @Column(name = "ritual_name", nullable = false, length = 255)
    private String ritualName;

    @Column(name = "date_lunar", columnDefinition = "TEXT")
    private String dateLunar;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id", foreignKey = @ForeignKey(name = "fk_ritual_region"))
    private Region region;

    @Column(name = "date_solar")
    private LocalDate dateSolar;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "meaning", columnDefinition = "TEXT")
    private String meaning;

    @Column(name = "image_url", columnDefinition = "TEXT")
    private String imageUrl;
}
