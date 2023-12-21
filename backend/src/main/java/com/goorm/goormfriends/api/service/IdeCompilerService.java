package com.goorm.goormfriends.api.service;

import com.goorm.goormfriends.db.entity.Ide;
import com.goorm.goormfriends.db.entity.ProblemTestCase;
import com.goorm.goormfriends.db.repository.IdeRepository;
import com.goorm.goormfriends.db.repository.ProblemTestCaseRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class IdeCompilerService {
//    private final IdeRepository ideRepository;
//    private final ProblemTestCaseRepository problemTestCaseRepository;
//
//    public void executeCode(Long ideId) {
//        Ide ide = ideRepository.findById(ideId).orElseThrow(
//                () -> new RuntimeException("IDE not found with id: " + ideId)
//        );
//
//        String userCode = ide.getUsercode();
//        Long problemId = ide.getProblem().getId();
//
//        // 해당 문제의 테스트 케이스 가져오기
//        List<ProblemTestCase> testCases = problemTestCaseRepository.findByProblemId(problemId);
//
//        boolean allTestsPassed = testCases.stream().allMatch(testCase -> {
//            //String output = externalCompilerService.compileAndRun(userCode, testCase.getInput());
//            return output.equals(testCase.getOutput());
//        });
//
//        ide.setState(allTestsPassed);
//        ideRepository.save(ide);
//    }

}
