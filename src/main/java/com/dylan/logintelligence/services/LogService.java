package com.dylan.logintelligence.services;

import com.dylan.logintelligence.DTOs.LogRequestDTO;
import com.dylan.logintelligence.DTOs.LogResponseDTO;
import com.dylan.logintelligence.exceptions.InvalidLogEntityException;
import com.dylan.logintelligence.models.LogEntity;
import com.dylan.logintelligence.repositories.LogRepository;
import com.dylan.logintelligence.utils.LogLevel;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class LogService {

    @Autowired
    private LogRepository logRepository;

    @Autowired
    private ModelMapper modelMapper;

    public LogResponseDTO saveLog(LogRequestDTO logRequestDTO) {
        if (logRequestDTO.getService() == null || logRequestDTO.getService().isBlank()) {
            throw new InvalidLogEntityException("Service name cannot be empty");
        }
        else{
        LogEntity logEntity = new LogEntity();
            logEntity.setService(logRequestDTO.getService());
            logEntity.setMessage(logRequestDTO.getMessage());
            try {
                logEntity.setLevel(LogLevel.valueOf(logRequestDTO.getLevel().toUpperCase()));
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Invalid log level");
            }
            try {
                logRepository.save(logEntity);
                return modelMapper.map(logEntity, LogResponseDTO.class);

            } catch (Exception e) {
                log.error("Error saving log: {} ",e.getMessage());
                throw new RuntimeException("Error saving log: "+ e);
    }}
    }
}
