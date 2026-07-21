package com.dylan.logintelligence.services;

import com.dylan.logintelligence.models.Alert;
import com.dylan.logintelligence.repositories.AlertRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AlertService {

    @Autowired
    private AlertRepository alertRepository;

    public void sendAlert(Alert alert) {
        alertRepository.save(alert);

    }
}
