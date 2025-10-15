package com.example.isp.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "checklist_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChecklistItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;

    @Column(nullable = false)
    private String itemName;

    private String itemDescription;

    @Column(nullable = false)
    private String unit;  // piece, set, kg, g, pack, other
}