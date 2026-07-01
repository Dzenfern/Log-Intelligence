package com.dylan.logintelligence.services;

import com.dylan.logintelligence.DTOs.LogRequestDTO;
import com.dylan.logintelligence.DTOs.LogResponseDTO;
import com.dylan.logintelligence.producers.LogProducer;
import com.dylan.logintelligence.repositories.LogRepository;
import com.dylan.logintelligence.utils.LogLevel;
import com.dylan.logintelligence.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LogService {

    private final LogProducer logProducer;
    private final LogRepository logRepository;
    private final Utils utils;

    public LogService(LogProducer logProducer, LogRepository logRepository,Utils utils) {
        this.utils = utils;
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

    public Page<LogResponseDTO> getLogs(int page, int size, String level, String service) {
        // Placeholder for pagination logic
        LogLevel logLevel = LogLevel.valueOf(level);
        Pageable pageable = PageRequest.of(page, size, Sort.by("timestamp").descending());
        Boolean hasLevel = level != null && !level.isEmpty();
        Boolean hasService = service != null && !service.isEmpty();
        if (hasLevel && hasService) {
            return logRepository.findByLevelAndService(logLevel, service, pageable)
                    .map(utils::mapToDTO);
        } else if (hasLevel) {
            return logRepository.findByLevel(logLevel, pageable)
                    .map(utils::mapToDTO);
        } else if (hasService) {
            return logRepository.findByService(service, pageable)
                    .map(utils::mapToDTO);
        } else {
            return logRepository.findAll(pageable)
                    .map(utils::mapToDTO);
        }
    }
}
