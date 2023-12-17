package com.goorm.goormfriends.api.service;

import com.goorm.goormfriends.api.dto.request.UpdateUserInfoRequest;
import com.goorm.goormfriends.api.dto.response.LoginResponse;
import com.goorm.goormfriends.api.dto.response.UserInfoRespone;

public interface UserService {
    String findUserEmailByUserId(int userId) throws Exception;

    String oauthLogin(String email) throws Exception;

    String reissue(String accessToken, String refreshToken) throws Exception;

    LoginResponse getLoginUser(String email) throws Exception;

    boolean existByEmail(String email) throws Exception;

    boolean existsByNickname(String nickname) throws Exception;

    UserInfoRespone setUserInfo(UpdateUserInfoRequest updateUserInfoRequest) throws Exception;
}
