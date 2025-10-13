package com.example.isp.model;

import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name = "staff")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Staff {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "staff_id")
    private Long staffId;

    @Column(name = "staff_name")
    private String staffName;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;
}
