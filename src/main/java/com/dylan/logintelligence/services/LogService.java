package com.dylan.logintelligence.services;

import com.dylan.logintelligence.DTOs.LogRequestDTO;
import com.dylan.logintelligence.kafka.LogProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LogService {

    private final LogProducer logProducer;

    public LogService(LogProducer logProducer) {
        this.logProducer = logProducer;
    }

    public void sendLog(LogRequestDTO logRequestDTO) {

        try {
            logProducer.sendLog(logRequestDTO);
        } catch (Exception e) {
            log.error("Error sending log to Kafka", e);
            throw new RuntimeException("Kafka publish failed: Service Failed", e);
        }
    }
}
