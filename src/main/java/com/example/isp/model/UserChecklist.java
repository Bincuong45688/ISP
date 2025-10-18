package com.example.isp.model;

import com.example.isp.model.enums.Ritual;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "user_checklists")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserChecklist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ritual_id", nullable = false)
    private Ritual ritual;

    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "userChecklist", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserChecklistItem> items;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }
}
