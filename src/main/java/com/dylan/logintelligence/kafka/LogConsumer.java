package com.dylan.logintelligence.kafka;

import com.dylan.logintelligence.DTOs.LogRequestDTO;
import com.dylan.logintelligence.DTOs.LogResponseDTO;
import com.dylan.logintelligence.models.LogEntity;
import com.dylan.logintelligence.repositories.LogRepository;
import com.dylan.logintelligence.utils.LogLevel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.security.Provider;
import java.time.LocalDateTime;

@Service
@Slf4j
public class LogConsumer {

    @Autowired
    private LogRepository logRepository;

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

        logRepository.save(entity);

        log.info("Saved log from Kafka: {}", entity.getId());
    }
}
