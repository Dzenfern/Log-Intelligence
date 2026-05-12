package com.dylan.logintelligence.controllers;

import com.dylan.logintelligence.DTOs.LogRequestDTO;
import com.dylan.logintelligence.services.LogService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
@RequestMapping("/logs")
public class LogController {

    private final LogService logService;

    public LogController(LogService logService) {
        this.logService = logService;
    }

// TODO: Eventual Consistency look into it

    @PostMapping("/")
    public ResponseEntity<String> createLog(@Valid @RequestBody LogRequestDTO dto) {

            logService.sendLog(dto);
            return new ResponseEntity<>("Log accepted for processing", HttpStatus.ACCEPTED);


    }
}
