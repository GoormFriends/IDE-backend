package com.goorm.goormfriends.common.oauth;

import java.util.Map;

public class GithubUserInfo implements OAuth2UserInfo{
    private final Map<String, Object> attributes;

    public GithubUserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }
    @Override
    public String getProviderId() {
        return ((Integer) attributes.get("id")).toString();
    }

    @Override
    public String getProvider() {
        return "github";
    }

    @Override
    public String getEmail() {
        return (String)attributes.get("email");
    }

    @Override
    public String getNickname() {
        return (String) attributes.get("login");
    }

    @Override
    public String getProfileImage() { return (String) attributes.get("avatar_url"); }
}

