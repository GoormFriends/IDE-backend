package com.goorm.goormfriends.api.dto.request;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class DirectoryProblemRequest {
    private Long userId;
    private Long directoryId;
    private Long problemId;
}
