package com.goorm.goormfriends.api.dto.request;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UpdateDirectoryRequest {
    private int userId;
    private int directoryId;
    private String newDirectoryTitle;
}
