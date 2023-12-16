package com.goorm.goormfriends.api.dto.request;

import com.goorm.goormfriends.db.entity.Problem;
import com.goorm.goormfriends.db.entity.User;
import lombok.Data;

@Data
public class IdeRequest {

    private String usercode;
    private Problem problem;
    private User user;
}
