package com.example.isp.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "checklistitems")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ChecklistItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long itemId;

    @Column(name = "item_name", nullable = false, length = 255)
    private String itemName;

    @Column(name = "unit", length = 255)
    private String unit;
}
