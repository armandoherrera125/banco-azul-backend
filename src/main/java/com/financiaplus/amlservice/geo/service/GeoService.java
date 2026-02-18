package com.financiaplus.amlservice.geo.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.financiaplus.amlservice.geo.dto.GeoResponse;

@Service
public class GeoService {

    private final RestTemplate restTemplate = new RestTemplate();

    public GeoResponse getGeoData() {

        String url = "https://ipapi.co/json/";

        GeoResponse response = restTemplate.getForObject(url, GeoResponse.class);

        return response;
    }
}