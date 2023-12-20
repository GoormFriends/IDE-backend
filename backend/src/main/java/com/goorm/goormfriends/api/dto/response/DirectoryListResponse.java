package com.goorm.goormfriends.api.dto.response;

import com.goorm.goormfriends.db.entity.CustomDirectory;
import com.goorm.goormfriends.db.entity.CustomDirectoryProblem;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
@ToString
@Getter
public class DirectoryListResponse {
    private final Long directoryId;
    private final String directoryName;
    private final List<DirectoryProblemResponse> problemList;

    public DirectoryListResponse(CustomDirectory customDirectory, List<DirectoryProblemResponse> customDirectoryProblemList) {
        this.directoryId = customDirectory.getId();
        this.directoryName = customDirectory.getDirectory_name();
        this.problemList = customDirectoryProblemList;
    }

}
