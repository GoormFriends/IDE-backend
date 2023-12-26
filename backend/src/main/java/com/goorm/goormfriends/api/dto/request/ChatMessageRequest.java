package com.goorm.goormfriends.api.dto.request;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ChatMessageRequest {

    private Long senderId;
    private Long problemId;
    private Long userId;
    private String message;
    private String userNickname;
    private String userProfile;
    private String time;

}
