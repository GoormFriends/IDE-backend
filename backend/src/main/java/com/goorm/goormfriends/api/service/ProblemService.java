package com.goorm.goormfriends.api.service;

import com.goorm.goormfriends.api.dto.response.ProblemResponse;
import com.goorm.goormfriends.db.entity.*;
import com.goorm.goormfriends.db.repository.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ProblemService {
    private final ProblemRepository problemRepository;
    private final IdeRepository ideRepository;
    private final CustomDirectoryRepository customDirectoryRepository;
    private final UserRepository userRepository;
    private final CustomDirectoryProblemRepository customDirectoryProblemRepository;

    // 특정 userId에 연결된 모든 Problem의 정보와 해당 Problem에 연결된 Ide 상태 및 CustomDirectory 이름을 반환
    public List<ProblemResponse> getProblemsByUserId(Long userId) {
        // 사용자 ID에 해당하는 모든 Problem을 조회
        List<Problem> problems = problemRepository.findAllByUserId(userId);

        // 해당 userId로 User 객체를 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        // 각 Problem에 대한 ProblemResponse 생성
        return problems.stream().map(problem -> {
            // 각 Problem에 연결된 Ide와 CustomDirectory 조회
            List<Ide> ides = ideRepository.findAllByProblemId(problem.getId());

            // 해당 Problem과 연관된 CustomDirectoryProblem 조회
            List<CustomDirectoryProblem> customDirectoryProblems = customDirectoryProblemRepository.findByProblemId(problem.getId());

            // 해당 Problem과 연관된 CustomDirectoryProblem을 기반으로 CustomDirectory 리스트 생성
            List<CustomDirectory> customDirectories = customDirectoryProblemRepository.findByProblemId(problem.getId())
                    .stream()
                    .filter(cdp -> cdp.getCustomDirectory().getUser().equals(user)) // 사용자와 연관된 것만 필터링
                    .map(CustomDirectoryProblem::getCustomDirectory)
                    .distinct() // 중복 제거
                    .collect(Collectors.toList());

            return ProblemResponse.from(problem, ides, customDirectories, user);
        }).collect(Collectors.toList());
    }
}
