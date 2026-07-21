package com.dylan.logintelligence.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;


@Table(
        name = "alerts",
        indexes = {
                @Index(name = "idx_type", columnList = "type"),
                @Index(name = "idx_category", columnList = "category"),
                @Index(name = "idx_timestamp", columnList = "timestamp")
        }
)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Alert {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String category;

    @Column(nullable = false,length = 1000)
    private String description;


    @CreationTimestamp
    private LocalDateTime timestamp;

}
