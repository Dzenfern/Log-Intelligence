package com.dylan.logintelligence.repositories;

import com.dylan.logintelligence.models.LogEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogRepository extends JpaRepository<LogEntity, Long> {
    Page<LogEntity> findByLevel(String level, Pageable pageable);
    Page<LogEntity> findByService(String service, Pageable pageable);
    Page<LogEntity> findByLevelAndService(String level, String service, Pageable pageable);
}
