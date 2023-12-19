package com.goorm.goormfriends.api.dto.response;

import com.goorm.goormfriends.db.entity.User;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class UserInfoRespone {
    private Integer id;
    private String nickname;
    private String profileImage;

    public UserInfoRespone(User user) {
        this.id = user.getId();
        this.nickname = user.getNickname();
        this.profileImage = user.getProfileImage();
    }
}
