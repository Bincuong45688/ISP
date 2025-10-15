package com.example.isp.model.enums;

import jakarta.persistence.*;
import lombok.*;

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

    @Column(nullable = false)
    private String region; // Miền Bắc / Miền Trung / Miền Nam

    @Column(nullable = false)
    private boolean active = true;
}