package com.dylan.logintelligence.DTOs;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class LogResponseDTO {

    private Long id;
    private String service;
    private String level;
    private String message;
    private LocalDateTime timestamp;
    private String category;
    private Double confidence;
    private String modelVersion;
}
