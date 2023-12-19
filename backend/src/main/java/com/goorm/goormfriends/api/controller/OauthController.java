package com.goorm.goormfriends.api.controller;

import com.goorm.goormfriends.api.dto.response.LoginResponse;
import com.goorm.goormfriends.api.service.UserService;
import com.goorm.goormfriends.common.jwt.TokenProvider;
import com.goorm.goormfriends.util.CookieUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("oauth2")
@RequiredArgsConstructor
public class OauthController {

    private final UserService userService;
    private final TokenProvider tokenProvider;

    @GetMapping
    public ResponseEntity<Map<String, Object>> accessToken(@RequestParam("accessToken") String accessToken, HttpServletRequest request,
                                                              HttpServletResponse response) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();

        Authentication authentication = tokenProvider.getAuthentication(accessToken);
        User user = (User) authentication.getPrincipal();

        String refreshToken = userService.oauthLogin(user.getUsername());
        CookieUtil.addCookie(request, response, "refreshToken", refreshToken);

        LoginResponse loginResponse = userService.getLoginUser(user.getUsername());
        resultMap.put("user", loginResponse);
        System.out.println(resultMap);
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }
}
