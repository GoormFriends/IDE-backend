package com.goorm.goormfriends.api.dto.response;

import com.goorm.goormfriends.db.entity.CustomDirectory;
import com.goorm.goormfriends.db.entity.Ide;
import com.goorm.goormfriends.db.entity.Problem;
import com.goorm.goormfriends.db.entity.State;
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
    private String level;

    private State ideState; // 단일 Boolean 값으로 수정
    private List<CustomDirectoryInfo> customDirectoryInfos; // CustomDirectoryInfo 리스트

    // Problem, Ide 리스트, CustomDirectory 리스트, 그리고 User 객체를 받아서 ProblemResponse 객체 생성
    public static ProblemResponse from(Problem problem, List<Ide> ides, List<CustomDirectory> customDirectories, User user) {
        // 해당 userId와 연관된 첫 번째 Ide의 상태만을 반환
        State ideState = ides.stream()
                .filter(ide -> ide.getUser().equals(user)) // 사용자와 연관된 Ide만 필터링
                .map(Ide::getState)
                .findFirst()
                .orElse(State.DEFAULT); // 사용자와 연관된 Ide가 없을 경우 기본 상태 반환

        // CustomDirectory가 null이 아닌 경우만 필터링
        List<CustomDirectoryInfo> directoryInfos = customDirectories.stream()
                .filter(directory -> directory != null && directory.getUser().equals(user)) // 사용자가 설정한 CustomDirectory만 필터링
                .map(directory -> new CustomDirectoryInfo(directory.getId(), directory.getDirectory_name()))
                .collect(Collectors.toList());

        return new ProblemResponse(
                problem.getId(),
                problem.getTitle(),
                problem.getLevel(),
                ideState,
                directoryInfos
        );
    }
}
