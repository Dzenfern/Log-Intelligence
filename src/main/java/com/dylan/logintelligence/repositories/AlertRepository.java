package com.dylan.logintelligence.repositories;

import com.dylan.logintelligence.models.Alert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlertRepository extends JpaRepository<Alert, String> {
    // Custom query methods can be defined here if needed
}
