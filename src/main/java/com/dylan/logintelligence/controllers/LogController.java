package com.dylan.logintelligence.controllers;

import com.dylan.logintelligence.DTOs.LogRequestDTO;
import com.dylan.logintelligence.DTOs.LogResponseDTO;
import com.dylan.logintelligence.services.LogService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Slf4j
@RequestMapping("/logs")
public class LogController {

    private final LogService logService;

    public LogController(LogService logService) {
        this.logService = logService;
    }

    @GetMapping("/")
    public ResponseEntity<List<LogResponseDTO>> getLogs(@RequestParam int page,
                                                        @RequestParam int size,
                                                        @RequestParam String level,
                                                        @RequestParam String service) {
        size = size<5 ? 10 : size; // Default to size 10 if not provided
        List<LogResponseDTO> logsPage = logService.getLogs(page, size,level,service);
        return new ResponseEntity<>(logsPage, HttpStatus.OK);
    }


//Pageable Page
//
//Filter by Level GET /logs?level=ERROR
//Filter by Service GET /logs?service=user-service
//Combined Filtering GET /logs?service=user-service&level=ERROR



    @PostMapping("/")
    public ResponseEntity<String> createLog(@Valid @RequestBody LogRequestDTO dto) {

            logService.sendLog(dto);
            return new ResponseEntity<>("Log accepted for processing", HttpStatus.ACCEPTED);


    }
}
