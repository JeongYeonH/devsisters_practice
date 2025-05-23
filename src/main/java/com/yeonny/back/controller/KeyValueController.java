package com.yeonny.back.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yeonny.back.dto.StorgeDto;
import com.yeonny.back.service.InMemoryStoreService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/storge")
@RequiredArgsConstructor
public class KeyValueController {

    private final InMemoryStoreService storeService;
    
    @PostMapping("/set-data")
    public ResponseEntity<Object> setKeyAndValue(
        @RequestBody StorgeDto storgeDto
    ){
        storeService.set(storgeDto.getKey(), storgeDto.getValue());
        return ResponseEntity.ok("저장되었습니다.");
    }
}
