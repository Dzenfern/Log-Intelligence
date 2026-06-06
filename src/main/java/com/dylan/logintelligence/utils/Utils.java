package com.dylan.logintelligence.utils;

import com.dylan.logintelligence.DTOs.LogResponseDTO;
import com.dylan.logintelligence.models.LogEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class Utils {

    private final ModelMapper modelMapper;

    public Utils(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public LogResponseDTO mapToDTO(LogEntity entity) {
        return modelMapper.map(entity, LogResponseDTO.class);
    }
}