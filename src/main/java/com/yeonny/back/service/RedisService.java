package com.yeonny.back.service;

import java.util.Map;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RedisService {
    
    private final RedisTemplate<String, Object> redisTemplate;

    public void saveMessageText(String roomName, Map<String, String> info){
        redisTemplate.opsForHash().putAll(roomName, info);
    }

    public Map<Object, Object> getMessageList(String roomName){
        return redisTemplate.opsForHash().entries(roomName);
    }
}
