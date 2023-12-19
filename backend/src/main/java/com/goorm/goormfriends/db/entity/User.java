package com.goorm.goormfriends.db.entity;

import com.goorm.goormfriends.api.dto.request.UpdateUserInfoRequest;
import com.goorm.goormfriends.common.oauth.OAuth2UserInfo;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="user")
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")

    private Long id;


    @Column(nullable = true)
    private String email; // 이메일

    @Column(nullable = true)
    private String nickname; // 이름

    @Column(nullable = true)
    private String profileImage; // 프로필 이미지

    @Column(nullable = false)
    private String provider; // 소셜 로그인 출처 ex. github, kakao

    @Column(nullable = false)
    private String providerId; // 소셜 로그인 ID


    @OneToMany(mappedBy = "user")
    private List<Ide> ides = new ArrayList<>();


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<CustomDirectory> customDirectory = new ArrayList<>();


    public User(OAuth2UserInfo user) {
        this.email = user.getEmail();
        this.nickname = user.getNickname();
        this.profileImage = user.getProfileImage();
        this.provider = user.getProvider();
        this.providerId = user.getProviderId();
    }
}
