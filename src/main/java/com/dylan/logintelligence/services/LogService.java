package com.dylan.logintelligence.services;

import com.dylan.logintelligence.DTOs.LogRequestDTO;
import com.dylan.logintelligence.DTOs.LogResponseDTO;
import com.dylan.logintelligence.kafka.LogProducer;
import com.dylan.logintelligence.repositories.LogRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class LogService {

    private final LogProducer logProducer;

    private final LogRepository logRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    public LogService(LogProducer logProducer, LogRepository logRepository) {
        this.logProducer = logProducer;
        this.logRepository = logRepository;
    }

    public void sendLog(LogRequestDTO logRequestDTO) {

        try {
            logProducer.sendLog(logRequestDTO);
        } catch (Exception e) {
            log.error("Error sending log to Kafka", e);
            throw new RuntimeException("Kafka publish failed: Service Failed", e);
        }
    }

    public List<LogResponseDTO> getLogs(int page, int size, String level, String service) {
        // Placeholder for pagination logic
        Pageable pageable = PageRequest.of(page, size);
        if (level != null && !level.isEmpty() && service != null && !service.isEmpty()) {
            return logRepository.findByLevelAndService(level, service, pageable)
                    .getContent().stream()
                    .map(logEntity -> modelMapper.map(logEntity, LogResponseDTO.class))
                    .toList();
        } else if (level != null && !level.isEmpty()) {
            return logRepository.findByLevel(level, PageRequest.of(page, size))
                    .getContent().stream()
                    .map(logEntity -> modelMapper.map(logEntity, LogResponseDTO.class))
                    .toList();
        } else if (service != null && !service.isEmpty()) {
            return logRepository.findByService(service, PageRequest.of(page, size))
                    .getContent().stream()
                    .map(logEntity -> modelMapper.map(logEntity, LogResponseDTO.class))
                    .toList();
        } else {
            return logRepository.findAll(PageRequest.of(page, size))
                    .getContent().stream()
                    .map(logEntity -> modelMapper.map(logEntity, LogResponseDTO.class))
                    .toList();
        }
    }
}
