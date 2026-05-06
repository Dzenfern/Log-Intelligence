package com.dylan.logintelligence.kafka;

import com.dylan.logintelligence.DTOs.LogRequestDTO;
import com.dylan.logintelligence.DTOs.LogResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class LogProducer {

    @Autowired
    private KafkaTemplate<String, LogResponseDTO> kafkaTemplate;

    public void sendLogToTopic(LogResponseDTO logResponseDTO) {
        kafkaTemplate.send("logs-topic",logResponseDTO.getService(), logResponseDTO);
    }

}
