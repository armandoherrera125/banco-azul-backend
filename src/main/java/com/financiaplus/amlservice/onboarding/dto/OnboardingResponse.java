package com.financiaplus.amlservice.onboarding.dto;

import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Schema(description = "Resultado del proceso de validación inicial del onboarding")
public class OnboardingResponse {

    @Schema(description = "Estado del proceso", example = "APPROVED")
    private String status;

    @Schema(description = "Motivo de rechazo si aplica", example = "USER_BLACKLISTED")
    private String reason;

    @Schema(description = "Indica si el cliente ya existe en la base", example = "true")
    private Boolean existingClient;

    @Schema(description = "Información geográfica obtenida por IP", example = """
            {
              "ip": "190.12.45.100",
              "country": "El Salvador",
              "region": "San Salvador",
              "city": "San Salvador"
            }
            """)
    private Object geoData;

    @Schema(description = "Información del cliente si ya existía en el sistema", example = """
            {
              "name": "Juan Pérez",
              "creditScore": 7.5,
              "monthlyIncome": 1200,
              "monthlyExpenses": 700
            }
            """)
    private Object clientData;
}