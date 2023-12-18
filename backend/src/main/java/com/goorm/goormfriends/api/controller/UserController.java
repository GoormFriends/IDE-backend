package com.goorm.goormfriends.api.controller;

import com.goorm.goormfriends.api.dto.request.UpdateUserInfoRequest;
import com.goorm.goormfriends.api.dto.response.LoginResponse;
import com.goorm.goormfriends.api.dto.response.UserInfoRespone;
import com.goorm.goormfriends.api.service.UserService;
import com.goorm.goormfriends.util.CookieUtil;
import com.nimbusds.oauth2.sdk.token.AccessToken;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 소셜 로그인
    @GetMapping("info")
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

    @PutMapping("info")
    public ResponseEntity<Map<String, Object>> updateUserInfo(@AuthenticationPrincipal User user,
                                                              @RequestBody UpdateUserInfoRequest updateUserInfoRequest) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();
        UserInfoRespone userInfoResponse = userService.setUserInfo(updateUserInfoRequest);
        resultMap.put("userInfo", userInfoResponse);

        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }

    @DeleteMapping("info")
    public ResponseEntity<Void> deleteUser(@AuthenticationPrincipal User user) throws Exception {

        com.goorm.goormfriends.db.entity.User deleteUser = userService.getUser(user.getUsername());

        if (deleteUser.getProvider().equals("kakao")) {
            String providerId = deleteUser.getProviderId();
            String reqURL = "https://kapi.kakao.com/v1/user/unlink";
            String APP_ADMIN_KEY = "c005abdbced5990e8031f821475b7e4a";

            try {
                URL url = new URL(reqURL);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Authorization", "KakaoAK " + APP_ADMIN_KEY);
                conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
                conn.setDoOutput(true); // 서버에서 받을 값이 있다면 true

                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
                StringBuilder sb = new StringBuilder();
                sb.append("target_id_type=user_id");
                sb.append("&target_id=" + providerId);
                bw.write(sb.toString());
                bw.flush();

                int responseCode = conn.getResponseCode();

                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                String result = "";
                String line = "";

                while ((line = br.readLine()) != null) {
                    result += line;
                }
                //System.out.println(result);

                br.close();
                bw.close();
                log.info("success delete kakao user");


            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {

        }
        log.info("delete User");
        userService.deleteUser(deleteUser);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("reissue")
    public ResponseEntity<Map<String, Object>> reissue(
            @RequestBody String accessToken,
            HttpServletRequest request) throws Exception {

        Cookie refreshTokenCookie = CookieUtil.getCookie(request, "refreshToken")
                .orElseThrow(() -> new RuntimeException("no refreshToken Cookie"));

        Map<String, Object> resultMap = new HashMap<>();

        accessToken = userService.reissue(accessToken, refreshTokenCookie.getValue());
        resultMap.put("accessToken", accessToken);

        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }
}

