package com.example.isp.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "checklists")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Checklist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "checklist_id")
    private Long checklistId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ritual_id", foreignKey = @ForeignKey(name = "fk_checklist_ritual"))
    private Ritual ritual;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", foreignKey = @ForeignKey(name = "fk_checklist_item"))
    private ChecklistItem item;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "check_note", length = 255)
    private String checkNote;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20)
    private ChecklistStatus status;
}
