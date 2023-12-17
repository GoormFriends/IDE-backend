package com.goorm.goormfriends.api.dto.response;

import com.goorm.goormfriends.db.entity.CustomDirectoryProblem;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class DirectoryProblemResponse {
    private final Long directProblem;
    private final Long problemNum;
    private final String problemTitle;

    public DirectoryProblemResponse(CustomDirectoryProblem customDirectoryProblem) {
        this.directProblem = customDirectoryProblem.getId();
        this.problemNum = customDirectoryProblem.getProblem().getId();
        this.problemTitle = customDirectoryProblem.getProblem().getTitle();
    }
}
