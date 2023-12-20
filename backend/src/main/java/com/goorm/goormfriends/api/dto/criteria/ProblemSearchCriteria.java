package com.goorm.goormfriends.api.dto.criteria;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProblemSearchCriteria {
    private Boolean state;
    private Long level;
    private String title;
}

// Criteria 는 조건, 기준, 항목 등 이라는 뜻. 검색 조건들이라는 의미로 사용.
