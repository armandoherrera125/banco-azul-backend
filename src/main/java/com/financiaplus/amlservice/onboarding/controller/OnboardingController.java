package com.financiaplus.amlservice.onboarding.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.*;

import com.financiaplus.amlservice.onboarding.dto.*;
import com.financiaplus.amlservice.onboarding.service.OnboardingService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/api/onboarding")
@Tag(name = "Onboarding", description = "Flujo de onboarding digital para apertura de cuenta y validaciones AML")
public class OnboardingController {

    private final OnboardingService service;

    public OnboardingController(OnboardingService service) {
        this.service = service;
    }

    @Operation(summary = "Iniciar onboarding de cliente", description = """
                Inicia el proceso de onboarding digital.

                Este endpoint valida:
                - Listas AML (blacklist)
                - Existencia del cliente
                - Elegibilidad por score crediticio
                - Geolocalización por IP

                Estados posibles:
                - APPROVED → el flujo continúa
                - REJECTED → usuario bloqueado por AML o score bajo
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Resultado del proceso de validación inicial"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida", content = @Content),
            @ApiResponse(responseCode = "403", description = "Cliente rechazado por AML o score", content = @Content)
    })
    @PostMapping("/start")
    public OnboardingResponse start(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Documento y nombre del cliente", required = true, content = @Content(schema = @Schema(implementation = OnboardingRequest.class))) @RequestBody OnboardingRequest request) {
        return service.startOnboarding(request);
    }

    @Operation(summary = "Completar onboarding", description = """
                Finaliza el proceso de onboarding y guarda la solicitud.

                Acciones realizadas:
                - Cálculo de nivel de riesgo financiero
                - Persistencia en base de datos
                - Marcado como APPROVED

                El nivel de riesgo se calcula usando:
                monthlyIncome / monthlyExpenses
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Onboarding completado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos financieros inválidos")
    })
    @PostMapping("/complete")
    public void complete(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos finales del onboarding", required = true, content = @Content(schema = @Schema(implementation = CompleteOnboardingRequest.class))) @RequestBody CompleteOnboardingRequest request) {
        service.completeOnboarding(request);
    }

    @Operation(summary = "Obtener métricas de onboarding", description = """
                Devuelve estadísticas generales del sistema:

                - Total de solicitudes
                - Solicitudes aprobadas
                - Solicitudes rechazadas
            """)
    @GetMapping("/metrics")
    public OnboardingMetrics metrics() {
        return service.getMetrics();
    }

    @Operation(summary = "Listar todas las solicitudes de onboarding", description = "Retorna todas las solicitudes almacenadas en la base de datos")
    @GetMapping("/all")
    public Map<String, Object> getAllApplications() {
        return service.getAllApplications();
    }

    @Operation(summary = "Buscar onboarding por documento", description = "Consulta una solicitud específica utilizando el documento del cliente")
    @GetMapping("/by-document/{document}")
    public Map<String, Object> getByDocument(
            @io.swagger.v3.oas.annotations.Parameter(description = "Documento del cliente", example = "12345678") @PathVariable String document) {
        return service.getApplicationByDocument(document);
    }
}