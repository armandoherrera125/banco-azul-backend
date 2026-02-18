package com.financiaplus.amlservice.onboarding.dto;

import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Schema(description = "Datos finales del cliente para completar el onboarding y calcular riesgo financiero")
public class CompleteOnboardingRequest {

    @Schema(description = "Documento del cliente", example = "12345678", required = true)
    private String document;

    @Schema(description = "Nombre completo del cliente", example = "Juan Pérez", required = true)
    private String fullName;

    @Schema(description = "Indica si el cliente ya existe en el sistema", example = "false", required = true)
    private Boolean existingClient;

    @Schema(description = "Ingreso mensual del cliente en USD", example = "1200.50", required = true)
    private Double monthlyIncome;

    @Schema(description = "Gastos mensuales del cliente en USD", example = "650.75", required = true)
    private Double monthlyExpenses;
}