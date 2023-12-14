package com.goorm.goormfriends.conig;

import com.goorm.goormfriends.common.jwt.JwtAuthenticationEntryPoint;
import com.goorm.goormfriends.common.jwt.TokenProvider;
import com.goorm.goormfriends.common.oauth.PrincipalOauth2UserService;
import com.goorm.goormfriends.filter.JwtFilter;
import com.goorm.goormfriends.handler.JwtAccessDeniedHandler;
import com.goorm.goormfriends.handler.OAuth2AuthenticationFailureHandler;
import com.goorm.goormfriends.handler.OAuth2AuthenticationSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@Component
public class SecurityConfig {
    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;
    private final PrincipalOauth2UserService principalOauth2UserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.httpBasic(HttpBasicConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                        .accessDeniedHandler(jwtAccessDeniedHandler)
                )
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
                )
                // 시큐리티는 기본적으로 세션 사용
                // 여기서는 세션 -> 쿠키 사용! 세션 설정을 Stateless로 설정
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // 로그인, 회원가입 API는 토큰이 없는 상태에서 요청 들어오기 때문에 permitAll 설정
                .authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                                //.requestMatchers(PERMIT_URL_ARRAY).hasAnyAuthority()
                                .anyRequest().permitAll()
                )
                .addFilterBefore(new JwtFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class)
                .oauth2Login(oauth2 -> oauth2
                                .userInfoEndpoint(userInfo -> userInfo.userService(principalOauth2UserService))
                                .successHandler(oAuth2AuthenticationSuccessHandler)
                                .failureHandler(oAuth2AuthenticationFailureHandler)
                        //.userService(principalOauth2UserService)
                );
        //.oauth2Login()
        //.userInfoEndpoint()
        //.userService(principalOauth2UserService)

        //.successHandler(oAuth2AuthenticationSuccessHandler)
        //.failureHandler(oAuth2AuthenticationFailureHandler);

        return http.build();

    }

    private static final String[] PERMIT_URL_ARRAY = {
            ""
    };
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        config.addAllowedOrigin("http://localhost:8081");
        config.addAllowedOrigin("http://localhost:8080");
        config.addAllowedMethod("*"); // 모든 메소드 허용.
        config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

}
