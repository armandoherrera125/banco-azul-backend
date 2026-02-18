package com.financiaplus.amlservice.aml.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.web.bind.annotation.*;

import com.financiaplus.amlservice.aml.entity.BlacklistedPerson;
import com.financiaplus.amlservice.aml.service.BlacklistService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/api/blacklist")
@Tag(name = "AML - Blacklist", description = "Validaciones AML contra listas negras regulatorias y de riesgo")
public class BlacklistController {

    private final BlacklistService service;

    public BlacklistController(BlacklistService service) {
        this.service = service;
    }

    @Operation(summary = "Verificar usuario en lista negra por documento", description = "Consulta registros AML para determinar si una persona está listada en bases de riesgo usando su documento de identidad.")
    @ApiResponse(responseCode = "200", description = "Resultado de verificación AML", content = @Content(schema = @Schema(example = """
            {
              "blacklisted": true,
              "reason": "MATCH PEP LIST"
            }
            """)))
    @GetMapping("/document/{document}")
    public Map<String, Object> checkByDocument(@PathVariable String document) {

        Optional<BlacklistedPerson> person = service.checkByDocument(document);

        Map<String, Object> response = new HashMap<>();

        if (person.isPresent()) {
            response.put("blacklisted", true);
            response.put("reason", person.get().getReason());
        } else {
            response.put("blacklisted", false);
        }

        return response;
    }

    @Operation(summary = "Verificar usuario en lista negra por nombre", description = "Realiza una búsqueda AML basada en coincidencias de nombre contra listas regulatorias y listas internas de riesgo.")
    @ApiResponse(responseCode = "200", description = "Resultado de verificación AML", content = @Content(schema = @Schema(example = """
            {
              "blacklisted": false
            }
            """)))
    @GetMapping("/name/{name}")
    public Map<String, Object> checkByName(@PathVariable String name) {

        Optional<BlacklistedPerson> person = service.checkByName(name);

        Map<String, Object> response = new HashMap<>();

        if (person.isPresent()) {
            response.put("blacklisted", true);
            response.put("reason", person.get().getReason());
        } else {
            response.put("blacklisted", false);
        }

        return response;
    }
}