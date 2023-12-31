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

        System.out.println("OAuth2AuthenticationSuccessHandler - onAuthenticationSuccess");
        String targetUrl = determineTargetUrl(request, response, authentication);
        if (response.isCommitted()) {
            return;
        }
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        User user = principalDetails.getUser();

        String targetUrl = "http://localhost/oauth";
        if (user.getNickname() != null || user.getProfileImage() != null) {
            String accessToken = tokenProvider.generateAccessToken(authentication);
            String refreshToken = tokenProvider.generateRefreshToken();

            RefreshToken rfToken = RefreshToken.builder()
                    .key(authentication.getName())
                    .value(refreshToken)
                    .build();

            refreshTokenRepository.save(rfToken);

            return UriComponentsBuilder.fromUriString(targetUrl + "?accessToken=" +accessToken)
                    .build().toUriString();
        }

        return UriComponentsBuilder.fromUriString(targetUrl+"?userId" +user.getId())
                .build().toUriString();
    }
}

