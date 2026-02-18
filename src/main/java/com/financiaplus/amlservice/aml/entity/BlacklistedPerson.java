package com.financiaplus.amlservice.aml.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "blacklisted_person")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlacklistedPerson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String document;

    @Column(name = "birth_date")
    private String birthDate;

    private String reason;
}