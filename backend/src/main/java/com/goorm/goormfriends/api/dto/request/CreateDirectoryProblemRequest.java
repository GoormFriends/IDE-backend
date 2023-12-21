package com.goorm.goormfriends.api.dto.request;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class CreateDirectoryProblemRequest {
    private Long userId;
    private Long directoryId;
    private Long problemId;
}