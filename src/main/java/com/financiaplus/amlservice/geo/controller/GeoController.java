package com.financiaplus.amlservice.geo.controller;

import org.springframework.web.bind.annotation.*;

import com.financiaplus.amlservice.geo.dto.GeoResponse;
import com.financiaplus.amlservice.geo.service.GeoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/api/geo")
@Tag(name = "Geolocation", description = "Servicios de geolocalización basados en la IP del usuario")
public class GeoController {

    private final GeoService geoService;

    public GeoController(GeoService geoService) {
        this.geoService = geoService;
    }

    @Operation(summary = "Obtener geolocalización del usuario", description = "Consulta el servicio externo ipapi para determinar la ubicación aproximada del usuario basada en su dirección IP.")
    @ApiResponse(responseCode = "200", description = "Geolocalización obtenida correctamente", content = @Content(schema = @Schema(implementation = GeoResponse.class)))
    @GetMapping
    public GeoResponse getGeo() {
        return geoService.getGeoData();
    }
}