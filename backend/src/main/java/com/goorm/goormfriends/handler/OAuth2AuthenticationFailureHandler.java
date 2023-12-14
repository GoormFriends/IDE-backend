package com.goorm.goormfriends.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
            throws IOException, ServletException, OAuth2AuthenticationException {
        if (exception instanceof OAuth2AuthenticationException) {
            log.error("소셜 로그인 에러 => {}", exception.getMessage());
            response.sendRedirect("/oauth/fail");
        } else {
            log.error("의도하지 않은 에러 => {}", exception.getMessage());
            response.sendRedirect("/login?error=" + exception.getMessage());
        }
    }
}