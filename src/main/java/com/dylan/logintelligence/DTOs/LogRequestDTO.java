package com.dylan.logintelligence.DTOs;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LogRequestDTO {

    @NotBlank
    private String service;

    @NotBlank
    private String level;

    @NotBlank
    private String message;
}
