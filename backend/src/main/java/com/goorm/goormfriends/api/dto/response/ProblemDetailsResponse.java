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
    private String title;
    private String usercode;
    private String content;
    private String level;
    private List<TestCaseInfo> testCases;

}
