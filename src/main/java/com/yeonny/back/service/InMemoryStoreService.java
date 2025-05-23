package com.yeonny.back.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

@Service
public class InMemoryStoreService {
    private Map<String, String> memory = new HashMap<>();
    private Map<String, Long> expireTimes = new HashMap<>();
    private ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    public void set(String key, String value){
        memory.put(key, value);
    }

    public String getValue(String key){
        return memory.get(key);
    }

    public void setExpireTime(String key, int seconds){
        long expireTime = System.currentTimeMillis() + seconds*1000L;
        expireTimes.put(key, expireTime);
        System.out.println(expireTimes);
    }

    public InMemoryStoreService(){
        scheduler.scheduleAtFixedRate(this::cleanUpKeys, 1, 1, TimeUnit.SECONDS);
    }

    private void cleanUpKeys(){
        long now = System.currentTimeMillis();
        for(String key : new HashSet<>(expireTimes.keySet())){
            if(expireTimes.get(key) <= now){
                memory.remove(key);
                expireTimes.remove(key);
                System.out.println("다음 키를 가진 객체가 삭제되었습니다: "+ key);
            }
        }
    }

}
