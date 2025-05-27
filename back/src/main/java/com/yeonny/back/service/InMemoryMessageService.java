package com.yeonny.back.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class InMemoryMessageService {
    private final Map<String, List<Map<String, Object>>> messageStore = new ConcurrentHashMap<>();

    //============ 재출 내역입니다. =================//
    private final String[][] courseArr = {
        {"1", " "},{"2", "*2"},{"3", "+4"},{"4", " "},{"5", "reverse"},{"6", "+4"},{"7", "*2"}
        ,{"8", "+4"},{"9", "+4"},{"10", "reverse"},{"11", "*2"}
    };
    private final Map<String, List<Integer>> userScores = new HashMap<>();

    public Map<String,List<Integer>> moveCookie (String userName, int moveRange){

        if(userScores.get(userName) == null){
            // defaultValue는 1: 시작위치, 3: 시작 체력, 1: 시작점수, 1: 시작방향. 입니다.
            List<Integer> defaultValue = Arrays.asList(1, 3, 1, 1);
            userScores.put(userName, defaultValue);
        }
        // 움직임이 없는 moveRange = 0 일 경우 그대로 반환을 합니다.
        if(moveRange == 0) return userScores;


        // defaultValue는 스코프에서 벗어난 선언이라 코드를 변경하였습니다.
        // 채력 이상의 움직임을 하려면 연산을 하지 않고 반환합니다.
        List<Integer> userStats = userScores.get(userName);
        if(moveRange > userStats.get(1)) return userScores;
        int direction = userStats.get(3);
        int movedPostion = userStats.get(0) + (moveRange * direction);
        // 이동한 위치가 배열 바깥일 경우, 로직을 진행하지 않습니다.
        if(movedPostion < 1 || movedPostion > 11) return userScores;
        int changedHealth = userStats.get(1) - moveRange;      
        String positionMark = courseArr[movedPostion-1][1];
        //reverse인 경우 방향을 -1을 곱해 전환합니다.
        if(positionMark == "reverse"){           
            direction = direction*(-1);
        }
        // chatGPT로 시간 부족으로 막혔던 "java 문자열 처리" 검색하였습니다.
        // 문자열이 숫자인 경우, int로 변환이 되지만, opertator의 경우 case문으로 처리하는 방식을 채택하였습니다.
        char operator = positionMark.charAt(0);
        int value = 1;
        // 순수 문자열인 경우 int 변환 에러가 생길 수 있으니, 조건문을 생성합니다.
        if(!positionMark.equals(" ") && !positionMark.equals("reverse")){
            value = Integer.parseInt(positionMark.substring(1));  
        } 
        // 맨 처음 점수는 기존 map에서 가져옵니다.
        int changedScore = userScores.get(userName).get(2);
        // case문으로 각각 연산을 합니다.
        switch (operator) {
            case '*':
                // 기존 점수에 곱을 합니다.
                changedScore = changedScore * value;
                break;
            case '+':
                // 기존 체력에 4를 더합니다.
                changedHealth = changedHealth + value;
                break;
            default:
                // 공백인 경우에 대해 잘 기억이 나지 않아, 움직인 만큼 점수를 얻는 것으로 추정을 하였습니다.
                changedScore =  changedScore + moveRange;
                break;
            }
     
        List<Integer> changedUserStats =  Arrays.asList(movedPostion, changedHealth, changedScore, direction);
        userScores.put(userName, changedUserStats);
        return userScores;
    }
    //============ 재출 내역입니다. =================//
    
    
    
    
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
