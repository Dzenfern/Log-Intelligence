package com.dylan.logintelligence.consumers;

import com.dylan.logintelligence.models.Alert;
import com.dylan.logintelligence.models.LogEntity;
import com.dylan.logintelligence.repositories.AlertRepository;
import com.dylan.logintelligence.services.AlertService;
import com.dylan.logintelligence.services.RedisCounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class AlertEngine {

    @Autowired
    private RedisCounterService redisCounterService;

    @Autowired
    private AlertRepository alertRepository;

    @Autowired
    private AlertService alertService;

    Map<String, Integer> errorRules = Map.of(
            "GENERAL", 5,
            "APPLICATION_ERROR", 3,
            "AUTH_ERROR", 1,
            "NETWORK_ERROR", 10,
            "DATABASE_ERROR", 5
    );
    @KafkaListener(topics = "logs-topic", groupId = "log-alerts")
    public void evaluate(LogEntity logEntity) {



        Integer recentAlerts =alertRepository.countRecentAlertsByType(
                logEntity.getCategory(),
                LocalDateTime.now().minusMinutes(5));
        Long count = redisCounterService.incrementCategory(logEntity.getCategory());

        if (recentAlerts == 0 && count >= errorRules.getOrDefault(logEntity.getCategory(), 5)) {
            createAlert(logEntity.getCategory());
        }

    }

    private Long getCategoryCount(String category) {
        return redisCounterService.getCount(category);
    }

    public void createAlert(String category){
        Alert alert = new Alert();
        alert.setDescription("ALERT: High number of " + category + " errors detected in the last 5 minutes!");
        alert.setCategory(category);
        alertService.sendAlert(alert);
        // Trigger an alert (e.g., send an email, log to a monitoring system, etc.)
        System.out.println("ALERT: High number of " + category + " errors detected in the last 5 minutes!");

    }
}
