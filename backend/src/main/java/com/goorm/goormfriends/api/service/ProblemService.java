package com.goorm.goormfriends.api.service;

import com.goorm.goormfriends.api.dto.response.ProblemResponse;
import com.goorm.goormfriends.db.repository.CustomDirectoryRepository;
import com.goorm.goormfriends.db.entity.Problem;
import com.goorm.goormfriends.db.entity.CustomDirectory;
import com.goorm.goormfriends.db.entity.Ide;
import com.goorm.goormfriends.db.repository.ProblemRepository;
import com.goorm.goormfriends.db.repository.IdeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProblemService {

    private final ProblemRepository problemRepository;
    private final IdeRepository ideRepository;
    private final CustomDirectoryRepository customDirectoryRepository;

    // 특정 userId에 연결된 모든 Problem의 정보와 해당 Problem에 연결된 Ide 상태 및 CustomDirectory 이름을 반환
    public List<ProblemResponse> getProblemsByUserId(Long userId) {
        // 사용자 ID에 해당하는 모든 Problem을 조회
        List<Problem> problems = problemRepository.findAllByUserId(userId);

        // 각 Problem에 대한 ProblemResponse 생성
        return problems.stream().map(problem -> {
            // 각 Problem에 연결된 Ide와 CustomDirectory 조회
            List<Ide> ides = ideRepository.findAllByProblemId(problem.getId());
            List<CustomDirectory> customDirectories = customDirectoryRepository.findAllByProblemId(problem.getId());

            // ProblemResponse 객체 생성 및 반환
            return ProblemResponse.from(problem, ides, customDirectories);
        }).collect(Collectors.toList());
    }
}
