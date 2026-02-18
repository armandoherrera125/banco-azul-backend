package com.financiaplus.amlservice.onboarding.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class OnboardingApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String document;
    private String fullName;

    private String status;
    private String rejectionReason;

    private Boolean existingClient;

    private Double monthlyIncome;
    private Double monthlyExpenses;

    private String riskLevel;

    private LocalDateTime createdAt;
}
