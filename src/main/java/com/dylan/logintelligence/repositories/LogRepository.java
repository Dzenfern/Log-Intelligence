package com.dylan.logintelligence.repositories;

import com.dylan.logintelligence.models.LogEntity;
import com.dylan.logintelligence.utils.LogLevel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;


@Repository
public interface LogRepository extends JpaRepository<LogEntity, Long> {
    Page<LogEntity> findByLevel(LogLevel level, Pageable pageable);
    Page<LogEntity> findByService(String service, Pageable pageable);
    Page<LogEntity> findByLevelAndService(LogLevel level, String service, Pageable pageable);

    @Query("SELECT COUNT(l) FROM LogEntity l WHERE l.category = :category AND l.timestamp >= :cutoff")
    Integer getErrorCountByCategory(String category, LocalDateTime cutoff);
}
