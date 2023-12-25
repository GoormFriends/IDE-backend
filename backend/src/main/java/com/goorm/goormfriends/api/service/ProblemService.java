package com.goorm.goormfriends.api.service;

import com.goorm.goormfriends.api.dto.criteria.ProblemSearchCriteria;
import com.goorm.goormfriends.api.dto.response.CustomDirectoryInfo;
import com.goorm.goormfriends.api.dto.response.ProblemDetailsResponse;
import com.goorm.goormfriends.api.dto.response.ProblemResponse;
import com.goorm.goormfriends.api.dto.response.TestCaseInfo;
import com.goorm.goormfriends.db.entity.*;
import com.goorm.goormfriends.db.repository.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.Predicate;
import com.goorm.goormfriends.db.repository.CustomDirectoryRepository;
import com.goorm.goormfriends.db.entity.Problem;
import com.goorm.goormfriends.db.entity.CustomDirectory;
import com.goorm.goormfriends.db.entity.Ide;
import com.goorm.goormfriends.db.repository.ProblemRepository;
import com.goorm.goormfriends.db.repository.IdeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
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
        List<Problem> problems = problemRepository.findAll(); // 모든 문제 조회

        return problems.stream().map(problem -> {
            List<Ide> ides = ideRepository.findAllByProblemId(problem.getId());
            List<CustomDirectoryProblem> customDirectoryProblems = customDirectoryProblemRepository.findByProblemId(problem.getId());

            // 해당 userId와 연관된 첫 번째 Ide의 상태를 추출 (없으면 기본 상태를 반환)
            State ideState = ides.stream()
                    .filter(ide -> ide.getUser().getId().equals(userId))
                    .map(Ide::getState)
                    .findFirst()
                    .orElse(State.DEFAULT); // 연관된 Ide가 없을 경우 기본 상태 반환

            // 해당 userId와 연관된 CustomDirectory 정보 추출 (없으면 빈 리스트 반환)
            List<CustomDirectoryInfo> customDirectoryInfos = customDirectoryProblems.stream()
                    .filter(cdp -> cdp.getCustomDirectory().getUser().getId().equals(userId))
                    .map(cdp -> new CustomDirectoryInfo(cdp.getCustomDirectory().getId(), cdp.getCustomDirectory().getDirectory_name()))
                    .collect(Collectors.toList());

            return new ProblemResponse(problem.getId(), problem.getTitle(), problem.getLevel(),
                    ideState, customDirectoryInfos); // 빈 리스트도 반환될 수 있음
        }).collect(Collectors.toList());
    }




    public ProblemDetailsResponse getProblemDetails(Long userId, Long problemId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        Problem problem = problemRepository.findById(problemId)
                .orElseThrow(() -> new EntityNotFoundException("Problem not found"));

        // Ide 데이터 확인 및 새로 생성
        Ide ide = ideRepository.findByUserIdAndProblemId(userId, problemId)
                .orElseGet(() -> createNewIde(user, problem));

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
                .map(ptc -> new TestCaseInfo(ptc.getId(),ptc.getInput(), ptc.getOutput()))
                .collect(Collectors.toList());

        // ProblemDetailsResponse 객체 생성 및 필드 설정
        ProblemDetailsResponse response = new ProblemDetailsResponse();
        response.setUserId(userId);
        response.setProblemId(problemId);
        response.setTitle(problem.getTitle());
        response.setUsercode(ide.getUsercode());
        response.setContent(problem.getContent());
        response.setLevel(problem.getLevel());
        response.setCustomDirectoryInfos(customDirectoryInfos);
        response.setTestCases(testCaseInfos); // 새로 추가된 필드 설정

        return response;
    }

    private Ide createNewIde(User user, Problem problem) {
        Ide newIde = new Ide();
        newIde.setUser(user);
        newIde.setProblem(problem);
        // 나머지 필드는 null로 초기화
        return ideRepository.save(newIde);
    }
}
