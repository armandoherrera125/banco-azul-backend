package com.financiaplus.amlservice.onboarding.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OnboardingMetrics {

    private long totalApplications;
    private long approved;
    private long rejected;
}
