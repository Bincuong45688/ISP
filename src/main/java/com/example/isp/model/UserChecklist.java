package com.example.isp.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user_checklists")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UserChecklist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_checklist_id")
    private Long userChecklistId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_user_checklist_customer"))
    private Customer user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ritual_id", nullable = false, foreignKey = @ForeignKey(name = "fk_user_checklist_ritual"))
    private Ritual ritual;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "reminder_date")
    private LocalDateTime reminderDate;

    @Builder.Default
    @Column(name = "is_notified")
    private Boolean isNotified = false;

    @Builder.Default
    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @OneToMany(mappedBy = "userChecklist", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserChecklistItem> items = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        if (isNotified == null) {
            isNotified = false;
        }
        if (isActive == null) {
            isActive = true;
        }
    }
}
