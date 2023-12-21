package com.goorm.goormfriends.api.dto.request;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class DeleteDirectoryRequest {
    private Long userId;
    private Long directoryId;
}
