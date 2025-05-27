package com.yeonny.back.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yeonny.back.service.ParthingDataService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/read")
@RequiredArgsConstructor
public class ReadMemoryController {
    
    private final ParthingDataService parthingDataService;

    @GetMapping("/csv-file")
    public ResponseEntity<?> readData(){
        List<List<String>> result = parthingDataService.readValue();
        return ResponseEntity.ok(result);
    }
}
