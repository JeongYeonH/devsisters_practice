package com.yeonny.back.service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class InMemoryStoreService {
    private Map<String, String> memory = new HashMap<>();
    private Map<String, Long> expireTimes = new HashMap<>();
    private ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String filePath = "storge.json";

    public void set(String key, String value){
        memory.put(key, value);
        saveData();
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
        loadData();
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

    private void saveData(){
        try{
            objectMapper.writeValue(new File(filePath), memory);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private void loadData(){
        try{
            File file = new File(filePath);
            if(file.exists()){
                memory = objectMapper.readValue(file, new TypeReference<Map<String, String>>() {});
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

}
