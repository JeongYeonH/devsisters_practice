package com.yeonny.back.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yeonny.back.dto.MessageDto;
import com.yeonny.back.dto.MoveDto;
import com.yeonny.back.service.InMemoryMessageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ChatController {
    
    private final InMemoryMessageService messageService;

    //======재출 내역입니다======//
    @PostMapping("/move")
    public ResponseEntity<?> moveForward(
        @RequestBody MoveDto moveDto
    ){
        String userName = moveDto.getUserName();
        Map<String,List<Integer>> result = messageService.moveCookie(userName, moveDto.getMoveRange());
        List<Integer> userStats = result.get(userName);
        return ResponseEntity.ok("사용자 닉네임: "+ userName
                                +" || 남은 채력: "+ userStats.get(1)
                                +", 획득한 점수: "+ userStats.get(2)
                                +", 현재 위치: "+ userStats.get(0)
                                +", 현재 방향: "+ userStats.get(3));
    }
    //======재출 내역입니다======//

    
    @GetMapping("/hi")
    public String hi(){
        return "hi";
    }

    @PostMapping("/{roomName}")
    public ResponseEntity<List<Object>> sendMessage(
        @PathVariable String roomName,
        @RequestBody MessageDto messageDto){

        Map<String, Object> messageInfo = new HashMap<>();
        String sentTime = LocalDateTime.now().toString();
        List<String> hasRead = new ArrayList<>();
        hasRead.add(messageDto.getUserName());
        
        messageInfo.put("userName", messageDto.getUserName());
        messageInfo.put("text", messageDto.getText());
        messageInfo.put("hasRead", hasRead);
        messageInfo.put("sentTime", sentTime);

        messageService.saveMessageText(roomName, messageInfo);
        
        List<Object> messageList = messageService.getMessageList(roomName);

        return ResponseEntity.ok(messageList);
    }

    @GetMapping("/message-list/{roomName}")
    public ResponseEntity<List<Object>> getMessageList(
        @PathVariable String roomName,
        @RequestParam String userName){

        List<Object> messageList = (List<Object>)(List<?>) messageService.getMessageListForSpecificUser(roomName, userName);
        
        return ResponseEntity.ok(messageList);
    }
}
