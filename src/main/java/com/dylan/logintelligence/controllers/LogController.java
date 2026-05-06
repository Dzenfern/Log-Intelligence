package com.dylan.logintelligence.controllers;

import com.dylan.logintelligence.DTOs.LogRequestDTO;
import com.dylan.logintelligence.DTOs.LogResponseDTO;
import com.dylan.logintelligence.services.LogService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@Slf4j
@RequestMapping("/logs")
public class LogController {

    @Autowired
    private LogService logService;


    @PostMapping("/")
    public ResponseEntity<LogResponseDTO> createLog( @RequestBody LogRequestDTO dto) {
            log.debug("Received log request: " + dto);
            LogResponseDTO logResponseDTO=  logService.saveLog(dto);
            return ResponseEntity.ok(logResponseDTO);
    }
}
