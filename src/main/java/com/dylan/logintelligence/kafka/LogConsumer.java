package com.dylan.logintelligence.kafka;

import com.dylan.logintelligence.DTOs.LogRequestDTO;
import com.dylan.logintelligence.DTOs.LogResponseDTO;
import com.dylan.logintelligence.DTOs.MLResponseDTO;
import com.dylan.logintelligence.models.LogEntity;
import com.dylan.logintelligence.repositories.LogRepository;
import com.dylan.logintelligence.services.MLClassificationService;
import com.dylan.logintelligence.utils.LogLevel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.security.Provider;
import java.time.LocalDateTime;
import java.util.Map;

@Service
@Slf4j
public class LogConsumer {

    @Autowired
    private LogRepository logRepository;

    @Autowired
    private MLClassificationService mlClassificationService;


    @KafkaListener(topics = "logs-topic", groupId = "log-consumers")
    public void consumeLog(LogRequestDTO logRequestDTO) {
        LogEntity entity = new LogEntity();
        entity.setService(logRequestDTO.getService());
        entity.setMessage(logRequestDTO.getMessage());
        entity.setTimestamp(LocalDateTime.now());


        try {
            entity.setLevel(LogLevel.valueOf(logRequestDTO.getLevel().toUpperCase()));
        } catch (Exception e) {
            log.error("Invalid log level", e);
            return; // skip bad message
        }

        MLResponseDTO mlResponse = mlClassificationService.classifyLog(logRequestDTO.getMessage());
        entity.setCategory(mlResponse.getCategory());
        entity.setConfidence(mlResponse.getConfidence());
        entity.setModelVersion(mlResponse.getModelVersion());

        logRepository.save(entity);

        log.info("Saved log from Kafka: {}", entity.getId());
    }
}
