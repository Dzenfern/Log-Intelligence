package com.dylan.logintelligence.services;

import com.dylan.logintelligence.models.Alert;
import com.dylan.logintelligence.repositories.AlertRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class AlertService {

    @Autowired
    private AlertRepository alertRepository;

    public void sendAlert(Alert alert) {
        alertRepository.save(alert);

    }
}
