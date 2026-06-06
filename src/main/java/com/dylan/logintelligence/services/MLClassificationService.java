package com.dylan.logintelligence.services;

import com.dylan.logintelligence.DTOs.MLResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@Slf4j
public class MLClassificationService {

    @Autowired
    private RestTemplate restTemplate;

    private final String url = "http://localhost:8000/classify";

    public MLResponseDTO classifyLog(String logMessage) {

        Map<String, String> body = Map.of(
                "message",
                logMessage
        );

        try {

            ResponseEntity<MLResponseDTO> response =
                    restTemplate.postForEntity(
                            url,
                            body,
                            MLResponseDTO.class
                    );

            return response.getBody();

        } catch (Exception e) {
            log.error("ML service unavailable", e);
            return new MLResponseDTO("UNKNOWN",0.0,"NONE"); // return empty response on failure
        }
    }

}
