package com.goorm.goormfriends.api.service;

import com.goorm.goormfriends.api.dto.response.CustomDirectoryInfo;
import com.goorm.goormfriends.api.dto.response.ProblemDetailsResponse;
import com.goorm.goormfriends.api.dto.response.ProblemResponse;
import com.goorm.goormfriends.api.dto.response.TestCaseInfo;
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
    private final UserRepository userRepository;
    private final CustomDirectoryRepository customDirectoryRepository;
    private final CustomDirectoryProblemRepository customDirectoryProblemRepository;
    private final ProblemTestCaseRepository problemTestCaseRepository;

    // 특정 userId에 연결된 모든 Problem의 정보와 해당 Problem에 연결된 Ide 상태 및 CustomDirectory 이름을 반환
    public List<ProblemResponse> getProblemsByUserId(Long userId) {
        List<Problem> problems = problemRepository.findAllByUserId(userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        return problems.stream().map(problem -> {
            List<Ide> ides = ideRepository.findAllByProblemId(problem.getId());

            // 해당 userId와 연관된 첫 번째 Ide의 상태만을 반환
            Boolean ideState = ides.stream()
                    .filter(ide -> ide.getUser().equals(user))
                    .map(Ide::isState)
                    .findFirst()
                    .orElse(null); // 사용자와 연관된 Ide가 없을 경우 null 반환

            List<CustomDirectoryProblem> customDirectoryProblems = customDirectoryProblemRepository.findByProblemId(problem.getId());
            List<CustomDirectoryInfo> customDirectoryInfos = customDirectoryProblems.stream()
                    .filter(cdp -> cdp.getCustomDirectory().getUser().equals(user))
                    .map(cdp -> new CustomDirectoryInfo(cdp.getCustomDirectory().getId(), cdp.getCustomDirectory().getDirectory_name()))
                    .collect(Collectors.toList());

            return new ProblemResponse(problem.getId(), problem.getTitle(), problem.getLevel(),
                    ideState,
                    customDirectoryInfos);
        }).collect(Collectors.toList());
    }

    public ProblemDetailsResponse getProblemDetails(Long userId, Long problemId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        Problem problem = problemRepository.findById(problemId)
                .orElseThrow(() -> new EntityNotFoundException("Problem not found"));
        Ide ide = ideRepository.findByUserIdAndProblemId(userId, problemId)
                .orElseThrow(() -> new EntityNotFoundException("Ide not found"));
        List<CustomDirectory> customDirectories = customDirectoryRepository.findByUserId(userId);
        if (customDirectories.isEmpty()) {
            throw new EntityNotFoundException("CustomDirectory not found for user id " + userId);
        }

        // CustomDirectoryInfo 리스트 생성
        List<CustomDirectoryProblem> customDirectoryProblems = customDirectoryProblemRepository.findByProblemId(problemId);
        List<CustomDirectoryInfo> customDirectoryInfos = customDirectoryProblems.stream()
                .filter(cdp -> cdp.getCustomDirectory().getUser().equals(user))
                .map(cdp -> new CustomDirectoryInfo(cdp.getCustomDirectory().getId(), cdp.getCustomDirectory().getDirectory_name()))
                .collect(Collectors.toList());

        // TestCaseInfo 리스트 생성
        List<TestCaseInfo> testCaseInfos = problemTestCaseRepository.findByProblemId(problemId)
                .stream()
                .map(ptc -> new TestCaseInfo(ptc.getInput(), ptc.getOutput()))
                .collect(Collectors.toList());

        // ProblemDetailsResponse 객체 생성 및 필드 설정
        ProblemDetailsResponse response = new ProblemDetailsResponse();
        response.setUserId(userId);
        response.setProblemId(problemId);
        response.setUsercode(ide.getUsercode());
        response.setContent(problem.getContent());
        response.setLevel(problem.getLevel());
        response.setCustomDirectoryInfos(customDirectoryInfos);
        response.setTestCases(testCaseInfos); // 새로 추가된 필드 설정

        return response;
    }
}
