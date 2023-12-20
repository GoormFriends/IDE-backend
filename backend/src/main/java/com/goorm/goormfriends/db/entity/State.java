package com.goorm.goormfriends.db.entity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor

public enum State {
    SUCCESS("성공"),
    COMPILE_ERROR("컴파일 에러"),
    WRONG_ANSWER("기대값과 불일치"),
    FAILURE("실패");

    private final String description;


    public String getDescription() {
        return description;
    }

}
