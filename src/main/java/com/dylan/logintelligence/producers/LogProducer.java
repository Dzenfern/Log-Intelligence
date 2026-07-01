package com.dylan.logintelligence.producers;

import com.dylan.logintelligence.DTOs.LogRequestDTO;
import com.dylan.logintelligence.DTOs.MLResponseDTO;
import com.dylan.logintelligence.models.LogEntity;
import com.dylan.logintelligence.services.MLClassificationService;
import com.dylan.logintelligence.utils.LogLevel;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class LogProducer {

    private final KafkaTemplate<String, LogEntity> kafkaTemplate;
    private final MLClassificationService mlClassificationService;
    private final ModelMapper modelMapper;


    public  LogProducer(KafkaTemplate<String, LogEntity> kafkaTemplate,
                        MLClassificationService mlClassificationService, ModelMapper modelMapper) {
         this.mlClassificationService = mlClassificationService;
        this.kafkaTemplate = kafkaTemplate;
        this.modelMapper = modelMapper;
    }

    public void sendLog(LogRequestDTO logRequestDTO) {
        MLResponseDTO mlResponseDTO = mlClassificationService.classifyLog(logRequestDTO.getMessage());
        LogEntity logEntity = new LogEntity();
        logEntity.setMessage(logRequestDTO.getMessage());
        logEntity.setService(logRequestDTO.getService());
        logEntity.setCategory(mlResponseDTO.getCategory());
        logEntity.setConfidence(mlResponseDTO.getConfidence());
        logEntity.setModelVersion(mlResponseDTO.getModelVersion());
        try {
            logEntity.setLevel(LogLevel.valueOf(logRequestDTO.getLevel()));
        } catch (Exception e) {
            log.error("Invalid log level ", e);
            logEntity.setLevel(LogLevel.ERROR);
        }

        kafkaTemplate
                .send("logs-topic",
                        logEntity.getService(),
                        logEntity)
                .whenComplete((result, ex) -> {

                    if (ex != null) {
                        log.error("Kafka send failed", ex);
                    } else {
                        log.info("Message sent to partition {}",
                                result.getRecordMetadata().partition());

                    }
                });

        }

    }


