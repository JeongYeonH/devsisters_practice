package com.yeonny.back.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class InMemoryStoreService {
    private Map<String, String> memory = new HashMap<>();
    private Map<String, Long> expireTimes = new HashMap<>();

    public void set(String key, String value){
        memory.put(key, value);
    }

    public void setExpireTime(String key, int seconds){
        long expireTime = System.currentTimeMillis() + seconds*1000L;
        expireTimes.put(key, expireTime);
    }

}
