package com.dylan.logintelligence.models;


import com.dylan.logintelligence.utils.LogLevel;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;


@Table(
        name = "logs",
        indexes = {
                @Index(name = "idx_service", columnList = "service"),
                @Index(name = "idx_level", columnList = "level"),
                @Index(name = "idx_timestamp", columnList = "timestamp")
        }
)
@Entity
@Data
@NoArgsConstructor
public class LogEntity {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String service;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LogLevel level;

    @Column(nullable = false,length = 1000)
    private String message;

    @CreationTimestamp
    private LocalDateTime timestamp;


}
