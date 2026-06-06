package com.dylan.logintelligence.controllers;

import com.dylan.logintelligence.DTOs.LogRequestDTO;
import com.dylan.logintelligence.DTOs.LogResponseDTO;
import com.dylan.logintelligence.services.LogService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
@RequestMapping("/logs")
public class LogController {

    private final LogService logService;

    public LogController(LogService logService) {
        this.logService = logService;
    }

    @GetMapping("/")
    public ResponseEntity<Page<LogResponseDTO>> getLogs(@RequestParam(required = false) int page,
                                                        @RequestParam(required = false) int size,
                                                        @RequestParam(required = false) String level,
                                                        @RequestParam(required = false) String service) {
        size = Math.min(size, 100); // Default to size 10 if not provided
        Page<LogResponseDTO> logsPage = logService.getLogs(page, size,level,service);
        return new ResponseEntity<>(logsPage, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<String> createLog(@Valid @RequestBody LogRequestDTO dto) {

            logService.sendLog(dto);
            return new ResponseEntity<>("Log accepted for processing", HttpStatus.ACCEPTED);


    }
}
