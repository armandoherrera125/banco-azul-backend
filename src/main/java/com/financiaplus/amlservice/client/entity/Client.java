package com.financiaplus.amlservice.client.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "client")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String document;

    @Column(unique = true)
    private String email;

    private String phone;

    @Column(name = "birth_date")
    private String birthDate;

    private String gender;

    private String state;

    private String address;

    private Double creditScore;

    private Double monthlyIncome;

    private Double monthlyExpenses;
}
