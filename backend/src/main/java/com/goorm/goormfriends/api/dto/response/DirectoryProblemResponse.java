package com.goorm.goormfriends.api.dto.response;

import com.goorm.goormfriends.db.entity.CustomDirectoryProblem;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class DirectoryProblemResponse {
    private final Long problemNum;
    private final Long problemLevel;
    private final String problemTitle;
    private final Long directoryProblemId;

    public DirectoryProblemResponse(CustomDirectoryProblem customDirectoryProblem, String problemTitle, Long problemLevel) {
        this.directoryProblemId = customDirectoryProblem.getId();
        this.problemNum = customDirectoryProblem.getProblem().getId();
        this.problemLevel = problemLevel;
        this.problemTitle = problemTitle;
    }
}
