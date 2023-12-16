package com.goorm.goormfriends.api.service;

import com.goorm.goormfriends.api.dto.response.LoginResponse;

public interface UserService {
    String findUserEmailByUserId(Long userId) throws Exception;

    String oauthLogin(String email) throws Exception;

    String reissue(String accessToken, String refreshToken) throws Exception;

    LoginResponse getLoginUser(String email) throws Exception;

    boolean existByEmail(String email) throws Exception;

    boolean existsByNickname(String nickname) throws Exception;
}
