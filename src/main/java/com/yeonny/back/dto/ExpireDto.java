package com.yeonny.back.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ExpireDto {
    private String key;
    private int seconds;
}
