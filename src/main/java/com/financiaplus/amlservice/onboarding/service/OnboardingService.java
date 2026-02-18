package com.financiaplus.amlservice.onboarding.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import com.financiaplus.amlservice.aml.service.BlacklistService;
import com.financiaplus.amlservice.client.service.ClientService;
import com.financiaplus.amlservice.geo.service.GeoService;
import com.financiaplus.amlservice.onboarding.dto.*;
import com.financiaplus.amlservice.onboarding.entity.OnboardingApplication;
import com.financiaplus.amlservice.onboarding.repository.OnboardingApplicationRepository;

@Service
public class OnboardingService {

    private static final Logger log = LoggerFactory.getLogger(OnboardingService.class);

    private final BlacklistService blacklistService;
    private final ClientService clientService;
    private final GeoService geoService;
    private final OnboardingApplicationRepository repository;

    public OnboardingService(
            BlacklistService blacklistService,
            ClientService clientService,
            GeoService geoService,
            OnboardingApplicationRepository repository) {

        this.blacklistService = blacklistService;
        this.clientService = clientService;
        this.geoService = geoService;
        this.repository = repository;
    }

    public OnboardingResponse startOnboarding(OnboardingRequest request) {

        log.info("Iniciando onboarding para documento {}", request.getDocument());

        OnboardingResponse response = new OnboardingResponse();

        var blacklisted = blacklistService.checkByDocument(request.getDocument());

        if (blacklisted.isPresent()) {

            log.warn("Usuario rechazado por blacklist {}", request.getDocument());

            saveRejected(request.getDocument(), request.getName(), "USER_BLACKLISTED");

            response.setStatus("REJECTED");
            response.setReason("USER_BLACKLISTED");
            return response;
        }

        var clientOpt = clientService.getClientByDocument(request.getDocument());

        if (clientOpt.isPresent()) {
            log.info("Cliente encontrado {}", request.getDocument());
            var client = clientOpt.get();

            if (!clientService.isEligible(client)) {

                log.warn("Usuario rechazado por score bajo {}", request.getDocument());

                saveRejected(request.getDocument(), request.getName(), "LOW_CREDIT_SCORE");

                response.setStatus("REJECTED");
                response.setReason("LOW_CREDIT_SCORE");
                return response;
            }

            response.setExistingClient(true);
            response.setClientData(client);

        } else {
            log.info("Cliente NO encontrado {}", request.getDocument());
            response.setExistingClient(false);
        }

        var geo = geoService.getGeoData();
        response.setGeoData(geo);
        response.setStatus("APPROVED");

        return response;
    }

    public void completeOnboarding(CompleteOnboardingRequest request) {

        log.info("Completando onboarding {}", request.getDocument());

        String riskLevel = calculateRisk(
                request.getMonthlyIncome(),
                request.getMonthlyExpenses());

        OnboardingApplication app = new OnboardingApplication();
        app.setDocument(request.getDocument());
        app.setFullName(request.getFullName());
        app.setStatus("APPROVED");
        app.setExistingClient(request.getExistingClient());
        app.setMonthlyIncome(request.getMonthlyIncome());
        app.setMonthlyExpenses(request.getMonthlyExpenses());
        app.setRiskLevel(riskLevel);
        app.setCreatedAt(LocalDateTime.now());

        repository.save(app);
    }

    public OnboardingMetrics getMetrics() {

        long total = repository.count();
        long approved = repository.findByStatus("APPROVED").size();
        long rejected = repository.findByStatus("REJECTED").size();

        return new OnboardingMetrics(total, approved, rejected);
    }

    public Map<String, Object> getAllApplications() {

        Map<String, Object> response = new HashMap<>();

        var apps = repository.findAll();

        response.put("count", apps.size());
        response.put("applications", apps);

        return response;
    }

    public Map<String, Object> getApplicationByDocument(String document) {

        Map<String, Object> response = new HashMap<>();

        var apps = repository.findAll()
                .stream()
                .filter(a -> a.getDocument().equals(document))
                .toList();

        response.put("count", apps.size());
        response.put("applications", apps);

        return response;
    }

    private void saveRejected(String document, String fullName, String reason) {

        OnboardingApplication app = new OnboardingApplication();
        app.setDocument(document);
        app.setFullName(fullName);
        app.setStatus("REJECTED");
        app.setRejectionReason(reason);
        app.setCreatedAt(LocalDateTime.now());

        repository.save(app);
    }

    private String calculateRisk(Double income, Double expenses) {

        if (income == null || expenses == null)
            return "MEDIUM";

        double ratio = income / expenses;

        if (ratio < 1)
            return "HIGH";
        if (ratio < 2)
            return "MEDIUM";
        return "LOW";
    }
}