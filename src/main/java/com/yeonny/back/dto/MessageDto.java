package com.yeonny.back.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class MessageDto {
    private String userName;
    private String text;
    private List<String> hasRead;
}
