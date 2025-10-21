package com.example.isp.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "checklistitems")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ChecklistItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "checklist_id")
    private Long checklistId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ritual_id", foreignKey = @ForeignKey(name = "fk_checklistitem_ritual"))
    private Ritual ritual;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", foreignKey = @ForeignKey(name = "fk_checklistitem_item"))
    private Checklist checklist;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "check_note", length = 255)
    private String checkNote;
}
