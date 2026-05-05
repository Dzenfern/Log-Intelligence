package com.dylan.logintelligence.controllers;

import com.dylan.logintelligence.models.LogEntity;
import com.dylan.logintelligence.repositories.LogRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@Slf4j
@RequestMapping("/logs")
public class LogController {

    @Autowired
    private LogRepository logRepository;


    @PostMapping("/")
    public void sendLogs(@RequestBody LogEntity logEntity){

        LogEntity logEntity1 = logRepository.save(logEntity);
        log.info(logEntity1.toString());
    }
}
