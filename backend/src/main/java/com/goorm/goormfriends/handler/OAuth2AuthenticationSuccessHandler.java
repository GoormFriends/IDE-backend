package com.goorm.goormfriends.handler;

import com.goorm.goormfriends.common.jwt.TokenProvider;
import com.goorm.goormfriends.common.oauth.PrincipalDetails;
import com.goorm.goormfriends.db.entity.RefreshToken;
import com.goorm.goormfriends.db.entity.User;
import com.goorm.goormfriends.db.repository.RefreshTokenRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    @Transactional
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {

        // redirect 할 url 지정해주기
        String targetUrl = determineTargetUrl(request, response, authentication);
        if (response.isCommitted()) {
            return;
        }
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        System.out.println(authentication);
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        User user = principalDetails.getUser();

        // 소셜 로그인 성공후 이동할 페이지
        String targetUrl = "http://localhost:3000/oauth";
        // 추가 정보가 입력되어 있다면 로그인 처리
        if (user.getNickname() != null || user.getProfileImage() != null) {
            // 토큰 정보 저장하는 페이지로 이동
            targetUrl = "http://localhost:3000/oauth2";
            //System.out.println("targetUrl/oauth2 " + targetUrl);
            // 인증 정보를 기반으로 토큰 생성~~
            String accessToken = tokenProvider.generateAccessToken(authentication);
            String refreshToken = tokenProvider.generateRefreshToken();

            RefreshToken rfToken = RefreshToken.builder()
                    .key(authentication.getName())
                    .value(refreshToken)
                    .build();

            refreshTokenRepository.save(rfToken);

            // 타켓 URL로 토큰 정보를 함께 보내줌
            return UriComponentsBuilder.fromUriString(targetUrl + "?accessToken=" +accessToken)
                    .build().toUriString();
        }

        return UriComponentsBuilder.fromUriString(targetUrl+"?userId" +user.getId())
                .build().toUriString();
    }
}

