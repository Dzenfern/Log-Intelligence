package com.dylan.logintelligence.services;

import com.dylan.logintelligence.DTOs.LogRequestDTO;
import com.dylan.logintelligence.DTOs.LogResponseDTO;
import com.dylan.logintelligence.kafka.LogProducer;
import com.dylan.logintelligence.models.LogEntity;
import com.dylan.logintelligence.utils.LogLevel;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LogService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private LogProducer logProducer;

    public LogResponseDTO sendLog(LogRequestDTO logRequestDTO) {
        LogEntity logEntity = new LogEntity();
        logEntity.setService(logRequestDTO.getService());
        logEntity.setMessage(logRequestDTO.getMessage());
        try {
            logEntity.setLevel(LogLevel.valueOf(logRequestDTO.getLevel().toUpperCase()));
        } catch (IllegalArgumentException e) {
            log.error("Invalid log level: {} ",logRequestDTO.getLevel());
            log.info("Defaulting log level to INFO");
            logEntity.setLevel(LogLevel.INFO); // Default to INFO if invalid level is provided
        }
        try {
            LogResponseDTO logResponseDTO = modelMapper.map(logEntity, LogResponseDTO.class);
            logProducer.sendLogToTopic(logResponseDTO);
            return ResponseEntity.ok(logResponseDTO).getBody();
        } catch (Exception e) {
            log.error("Error sending log to Kafka", e);
            throw new RuntimeException("Kafka publish failed");
        }
    }
}
