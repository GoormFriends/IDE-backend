package com.goorm.goormfriends.api.dto.response;

import com.goorm.goormfriends.db.entity.CustomDirectoryProblem;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class DirectoryProblemResponse {
    private final Long directoryId;
    private final Long problemId;
    private final Long directoryProblemId;

    public DirectoryProblemResponse(CustomDirectoryProblem customDirectoryProblem) {
        this.directoryProblemId = customDirectoryProblem.getId();
        this.problemId = customDirectoryProblem.getProblem().getId();
        this.directoryId = customDirectoryProblem.getCustomDirectory().getId();
    }
}
