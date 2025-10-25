package com.example.isp.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "shipper")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Shipper {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shipper_id")
    private Long shipperId;

    @Column(name = "shipper_name", nullable = false)
    private String shipperName;

    @Column(name = "gender")
    private String gender;

    @OneToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;
}
