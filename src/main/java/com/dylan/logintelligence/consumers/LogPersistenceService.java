package com.dylan.logintelligence.consumers;

import com.dylan.logintelligence.models.LogEntity;
import com.dylan.logintelligence.repositories.LogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LogPersistenceService {

    @Autowired
    private LogRepository logRepository;

    @KafkaListener(topics = "logs-topic", groupId = "log-persistence")
    public void consumeLog(LogEntity logEntity) {

        logRepository.save(logEntity);

        log.info("Saved log from Kafka: {}", logEntity.getId());
    }
}
