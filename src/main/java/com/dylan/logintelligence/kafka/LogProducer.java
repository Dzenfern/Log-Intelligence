package com.dylan.logintelligence.kafka;

import com.dylan.logintelligence.DTOs.LogRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LogProducer {

    private final KafkaTemplate<String, LogRequestDTO> kafkaTemplate;

    public  LogProducer(KafkaTemplate<String, LogRequestDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendLog(LogRequestDTO logRequestDTO) {
        kafkaTemplate
                .send("logs-topic",
                        logRequestDTO.getService(),
                        logRequestDTO)
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


