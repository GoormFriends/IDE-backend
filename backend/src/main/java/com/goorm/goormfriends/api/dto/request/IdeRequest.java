package com.goorm.goormfriends.api.dto.request;

import com.goorm.goormfriends.db.entity.Problem;
import com.goorm.goormfriends.db.entity.User;
import lombok.Data;

@Data
public class IdeRequest {

    private Long userId; //유저아이디
    private Long problemId; //문제넘버
    private String usercode; //유저코드
}
