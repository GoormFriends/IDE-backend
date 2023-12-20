package com.goorm.goormfriends.api.dto.response;

import com.goorm.goormfriends.db.entity.CustomDirectory;
import com.goorm.goormfriends.db.entity.Ide;
import com.goorm.goormfriends.db.entity.Problem;
import com.goorm.goormfriends.db.entity.State;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProblemResponse {
    private Long problemId;
    private String title;
    private Long level;
    private List<State> idesStates; // 사용자의 Ide 상태 목록
    private List<String> customDirectoryNames; // 사용자의 CustomDirectory 이름 목록

    // Problem, Ide 리스트, CustomDirectory 리스트를 받아서 ProblemResponse 객체 생성
    public static ProblemResponse from(Problem problem, List<Ide> ides, List<CustomDirectory> customDirectories) {
        List<State> idesStates = ides.stream()
                .map(Ide::getState)
                .collect(Collectors.toList());

        List<String> directoryNames = customDirectories.stream()
                .map(CustomDirectory::getDirectory_name)
                .collect(Collectors.toList());

        return new ProblemResponse(
                problem.getId(),
                problem.getTitle(),
                problem.getLevel(),
                idesStates,
                directoryNames
        );
    }
}