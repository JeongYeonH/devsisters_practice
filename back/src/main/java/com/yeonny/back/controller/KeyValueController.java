package com.yeonny.back.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yeonny.back.dto.ExpireDto;
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

    @PostMapping("/set-expire-time")
    public ResponseEntity<?> setExpireTime(
        @RequestBody ExpireDto expireDto
    ){
        storeService.setExpireTime(expireDto.getKey(), expireDto.getSeconds());
        return ResponseEntity.ok("만료기간이 설정되었습니다.");
    }

    @GetMapping("/get-value/{key}")
    public ResponseEntity<String> getValue(
        @PathVariable String key
    ){
        String value = storeService.getValue(key);
        return ResponseEntity.ok(value);
    }
}
