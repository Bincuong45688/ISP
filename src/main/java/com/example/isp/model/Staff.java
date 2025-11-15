package com.example.isp.model;

import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name = "staff")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Staff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "staff_id")
    private Long staffId;

    @Column(name = "staff_name", nullable = false)
    private String staffName;

    @Column(name = "gender")
    private String gender;

    @OneToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

}
