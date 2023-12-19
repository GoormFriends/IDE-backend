package com.goorm.goormfriends.api.dto.request;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UpdateUserInfoRequest {
    private Long userId;
    private String name; // nickname 개념으로 보면 될듯
    private String profileImage;
}
