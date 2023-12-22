package com.goorm.goormfriends.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestCaseInfo {
    private Long TestCaseId;
    private String input;
    private String output;
}
