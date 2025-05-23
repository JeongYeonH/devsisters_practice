package com.yeonny.back.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class InMemoryMessageService {
    private final Map<String, List<Map<String, Object>>> messageStore = new ConcurrentHashMap<>();

    public synchronized void saveMessageText(String roomName, Map<String, Object> messageInfo){
        messageStore.computeIfAbsent(roomName, k -> new ArrayList<>()).add(messageInfo);
    }

    public List<Object> getMessageList(String roomName){
        return new ArrayList<>(messageStore.getOrDefault(roomName, Collections.emptyList()));
    }

    public List<Map<String,Object>> getMessageListForSpecificUser(String roomName, String userName){
        List<Map<String,Object>> messageList = messageStore.getOrDefault(roomName, Collections.emptyList());
        List<Map<String,Object>> filteredList = messageList.stream()
            .filter( msg -> {
                List<String> hasRead = (List<String>) msg.get("hasRead");
                return !hasRead.contains(userName);
            })
            .collect(Collectors.toList());
        return new ArrayList<>(filteredList);
    }
}
