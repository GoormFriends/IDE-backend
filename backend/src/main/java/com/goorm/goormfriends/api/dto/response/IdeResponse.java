package com.goorm.goormfriends.api.dto.response;

import com.goorm.goormfriends.db.entity.Ide;
import com.goorm.goormfriends.db.entity.ProblemTestCase;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor

public class IdeResponse {

    private Long id; //ide
    private boolean state; //state 바꿀예정 문제 정답결과
    private String usercode; //
    private Long userId;
    private String problemId;
    private Integer level;
    private String testCaseInput; // 테스트 케이스 입력
    private String testCaseOutput; // 테스트 케이스 출력

    public static IdeResponse from(Ide ide, ProblemTestCase testCase) {

        return new IdeResponse(
                ide.getId(),
                ide.isState(),
                ide.getUsercode(),
                ide.getUser() != null ? ide.getUser().getId() : null,
                ide.getProblem() != null ? ide.getProblem().getId() : null,
                ide.getProblem() != null ? ide.getProblem().getLevel() : null,
                testCase != null ? testCase.getInput() : null,
                testCase != null ? testCase.getOutput() : null
        );
    }

}
