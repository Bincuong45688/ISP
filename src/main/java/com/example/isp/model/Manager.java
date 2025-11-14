package com.example.isp.model;

import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name = "manager")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Manager {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "manager_id")
    private Long managerId;

    @Column(name = "manager_name")
    private String managerName;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;
}
