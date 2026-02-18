package com.financiaplus.amlservice.geo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Información de geolocalización obtenida a partir de la IP del usuario usando ipapi")
public class GeoResponse {

    @Schema(description = "Dirección IP pública del usuario detectada por el servicio", example = "190.53.10.25")
    private String ip;

    @Schema(description = "País detectado según la IP", example = "El Salvador")
    private String country_name;

    @Schema(description = "Región o departamento del usuario", example = "San Salvador")
    private String region;

    @Schema(description = "Ciudad detectada", example = "San Salvador")
    private String city;
}