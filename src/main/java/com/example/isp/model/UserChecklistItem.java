package com.example.isp.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_checklist_items")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UserChecklistItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_checklist_item_id")
    private Long userChecklistItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_checklist_id", nullable = false, foreignKey = @ForeignKey(name = "fk_user_checklist_item_checklist"))
    private UserChecklist userChecklist;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false, foreignKey = @ForeignKey(name = "fk_user_checklist_item_item"))
    private ChecklistItem item;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "checked")
    private Boolean checked = false;

    @Column(name = "note")
    private String note;

    @PrePersist
    protected void onCreate() {
        if (checked == null) {
            checked = false;
        }
    }
}
