package com.financiaplus.amlservice.onboarding.dto;

import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Schema(description = "Solicitud inicial para iniciar el proceso de onboarding digital")
public class OnboardingRequest {

    @Schema(description = "Nombre completo del cliente", example = "Juan Pérez", required = true)
    private String name;

    @Schema(description = "Documento de identidad del cliente", example = "12345678", required = true)
    private String document;

    @Schema(description = "Número de teléfono del cliente", example = "+50370001234")
    private String phone;

    @Schema(description = "Correo electrónico del cliente", example = "juan.perez@email.com")
    private String email;
}