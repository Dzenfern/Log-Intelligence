package com.dylan.logintelligence.repositories;

import com.dylan.logintelligence.models.Alert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface AlertRepository extends JpaRepository<Alert, Long> {
    // Custom query methods can be defined here if needed
    @Query("SELECT COUNT(a) FROM Alert a WHERE a.type = :category AND a.timestamp >= :cutoff")
    Integer countRecentAlertsByType(String category, LocalDateTime cutoff);
}
