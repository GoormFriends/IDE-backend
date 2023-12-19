package com.goorm.goormfriends.api.service;

import com.goorm.goormfriends.api.dto.request.UpdateUserInfoRequest;
import com.goorm.goormfriends.api.dto.response.LoginResponse;
import com.goorm.goormfriends.api.dto.response.UserInfoRespone;
import com.goorm.goormfriends.db.entity.User;

public interface UserService {
    String findUserEmailByUserId(Long userId) throws Exception;

    String oauthLogin(String email) throws Exception;

    String reissue(String accessToken, String refreshToken) throws Exception;

    User getUser(String email) throws Exception;

    LoginResponse getLoginUser(String email) throws Exception;

    boolean existByEmail(String email) throws Exception;

    boolean existsByNickname(String nickname) throws Exception;

    UserInfoRespone setUserInfo(UpdateUserInfoRequest updateUserInfoRequest) throws Exception;

    void deleteUser(User user) throws Exception;
}
