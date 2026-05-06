package com.dylan.logintelligence.kafka;

import com.dylan.logintelligence.DTOs.LogResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LogConsumer {

    @KafkaListener(topics = "logs-topic", groupId = "log-consumers")
    public void consumeLog(LogResponseDTO logResponseDTO) {
        // Placeholder for Kafka consumer logic
        // In a real implementation, this would listen to the Kafka topic and process incoming log messages
        log.info("Consumed log from Kafka: Service={}, Level={}, Message={}",
                logResponseDTO.getService(), logResponseDTO.getLevel(), logResponseDTO.getMessage());
    }
}
