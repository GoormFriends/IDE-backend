package com.goorm.goormfriends.api.dto.request;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CreateDirectoryRequest {
    private int userId;
    private String directoryTitle;
}
