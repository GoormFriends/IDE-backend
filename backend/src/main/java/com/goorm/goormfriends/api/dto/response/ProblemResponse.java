package com.goorm.goormfriends.api.dto.response;

import com.goorm.goormfriends.db.entity.CustomDirectory;
import com.goorm.goormfriends.db.entity.Ide;
import com.goorm.goormfriends.db.entity.Problem;
import com.goorm.goormfriends.db.entity.User;
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
    private List<Boolean> idesStates; // 특정 사용자의 Ide 상태 목록
    private List<String> customDirectoryNames; // 특정 사용자의 CustomDirectory 이름 목록

    // Problem, Ide 리스트, CustomDirectory 리스트, 그리고 User 객체를 받아서 ProblemResponse 객체 생성
    public static ProblemResponse from(Problem problem, List<Ide> ides, List<CustomDirectory> customDirectories, User user) {
        List<Boolean> idesStates = ides.stream()
                .filter(ide -> ide.getUser().equals(user)) // 사용자가 설정한 Ide만 필터링
                .map(Ide::isState)
                .collect(Collectors.toList());

        List<String> directoryNames = customDirectories.stream()
                .filter(directory -> directory.getUser().equals(user)) // 사용자가 설정한 CustomDirectory만 필터링
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