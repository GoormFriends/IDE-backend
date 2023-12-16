package com.goorm.goormfriends.api.dto.response;

import com.goorm.goormfriends.db.entity.User;
import lombok.Getter;
import lombok.ToString;


@ToString
@Getter
public class LoginResponse {

    private final Long userId;
    private final String nickname;
    private final String profileImage;
    private final String email;

    public LoginResponse(User user) {
        this.userId = user.getId();
        this.nickname = user.getNickname();
        this.profileImage = user.getProfileImage();
        this.email = user.getEmail();
    }
}