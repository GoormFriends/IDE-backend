package com.goorm.goormfriends.api.dto.request;

import com.goorm.goormfriends.db.entity.Problem;
import com.goorm.goormfriends.db.entity.User;
import lombok.Data;

@Data
public class IdeRequest {

    private String usercode; //유저코드
    private Problem problem; //문제넘버
    private User user; //유저아이디
}
