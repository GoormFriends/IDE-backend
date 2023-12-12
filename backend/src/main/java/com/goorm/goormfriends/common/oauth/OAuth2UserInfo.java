package com.goorm.goormfriends.common.oauth;

public interface OAuth2UserInfo {
    String getProvider();
    String getEmail();
    String getName();
    String getProfileImage();
}
