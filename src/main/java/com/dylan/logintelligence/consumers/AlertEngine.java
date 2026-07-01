package com.dylan.logintelligence.consumers;

import com.dylan.logintelligence.models.Alert;
import com.dylan.logintelligence.models.LogEntity;
import com.dylan.logintelligence.repositories.LogRepository;
import com.dylan.logintelligence.services.AlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AlertEngine {

    @Autowired
    private LogRepository logRepository;

    @Autowired
    private AlertService alertService;

    @KafkaListener(topics = "logs-topic", groupId = "log-alerts")
    public void evaluate(LogEntity logEntity) {

        LocalDateTime cutoffTime = LocalDateTime.now().minusMinutes(5);
        // Placeholder for alert evaluation logic
        // In a real implementation, this would analyze the log entry and trigger alerts if necessary
        Integer count = logRepository.getErrorCountByCategory(logEntity.getCategory(),cutoffTime);
        if (count != null && count > 5) {
            Alert alert = new Alert();

            alert.setDescription("ALERT: High number of " + logEntity.getCategory() + " errors detected in the last 5 minutes!");
            alert.setType(logEntity.getCategory());
            alertService.sendAlert(alert);
            // Trigger an alert (e.g., send an email, log to a monitoring system, etc.)
            System.out.println("ALERT: High number of " + logEntity.getCategory() + " errors detected in the last 5 minutes!");

        }

    }
}
