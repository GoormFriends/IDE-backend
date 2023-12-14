package com.goorm.goormfriends.api.controller;

import com.goorm.goormfriends.api.dto.response.LoginResponse;
import com.goorm.goormfriends.api.service.UserService;
import com.goorm.goormfriends.util.CookieUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    // 소셜 로그인
    @GetMapping("login")
    public ResponseEntity<Map<String, Object>> oauthLogin(@AuthenticationPrincipal User user,
                                                          HttpServletRequest request,
                                                          HttpServletResponse response) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();

        String refreshToken = userService.oauthLogin(user.getUsername());
        CookieUtil.addCookie(request, response, "refreshToken", refreshToken);

        LoginResponse loginResponse = userService.getLoginUser(user.getUsername());
        resultMap.put("user", loginResponse);

        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }
}
