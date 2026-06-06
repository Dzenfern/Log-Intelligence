package com.dylan.logintelligence.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MLResponseDTO {

    private String category;
    private Double confidence;
    private String ModelVersion;
}