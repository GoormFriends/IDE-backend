package com.goorm.goormfriends.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProblemDetailsResponse {
    private Long userId;
    private Long problemId;
    private String usercode;
    private String content;
    private Long level;
    private List<CustomDirectoryInfo> customDirectoryInfos;
    private List<TestCaseInfo> testCases;

}
