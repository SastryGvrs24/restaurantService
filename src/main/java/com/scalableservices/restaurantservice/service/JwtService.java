package com.scalableservices.restaurantservice.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scalableservices.restaurantservice.dto.JwtValidationResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class JwtService {

    private final RestTemplate restTemplate;

    public JwtService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public JwtValidationResponse validateJwt(String jwt) {
        String validationUrl = "http://localhost:3000/auth/validateToken";

        // Add the token to the request headers
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", "token=" + jwt);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        try {
            // Send a GET request with headers
            ResponseEntity<String> response =
                    restTemplate.exchange(validationUrl,
                            org.springframework.http.HttpMethod.GET,
                            entity,
                            String.class);

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            JwtValidationResponse jwtValidationResponseBody = objectMapper.readValue(response.getBody(), JwtValidationResponse.class);

            return jwtValidationResponseBody;
        } catch (Exception e) {
            // Log the error and return null if validation fails
            e.printStackTrace();
            return null;
        }
    }
}