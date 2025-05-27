package com.yeonny.back.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name="message")
@Table(name="message")
public class MessageEntity {
    
}
