package com.financiaplus.amlservice.onboarding.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.financiaplus.amlservice.onboarding.entity.OnboardingApplication;

import java.util.List;

public interface OnboardingApplicationRepository
        extends JpaRepository<OnboardingApplication, Long> {

    List<OnboardingApplication> findByStatus(String status);
}
