package com.goorm.goormfriends.api.dto.request;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ChatMessageRequest {

    private Integer messageType;
    private Long userId;
    private String message;
}
