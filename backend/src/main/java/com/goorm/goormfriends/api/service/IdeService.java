package com.goorm.goormfriends.api.service;

import com.goorm.goormfriends.api.dto.request.IdeRequest;
import com.goorm.goormfriends.api.dto.response.IdeCompilerResponse;
import com.goorm.goormfriends.api.dto.response.IdeResponse;
import com.goorm.goormfriends.db.entity.Ide;
import com.goorm.goormfriends.db.entity.Problem;
import com.goorm.goormfriends.db.entity.ProblemTestCase;
import com.goorm.goormfriends.db.entity.User;
import com.goorm.goormfriends.db.repository.IdeRepository;
import com.goorm.goormfriends.db.repository.ProblemRepository;
import com.goorm.goormfriends.db.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class IdeService {
    private final IdeRepository ideRepository;
    private final ProblemRepository problemRepository;
    private final UserRepository userRepository;
    private final IdeCompilerService ideCompilerService;


    public IdeResponse addInput(Long userId, Long problemId, IdeRequest request) {
        Problem problem = problemRepository.findById(request.getProblemId())
                .orElseThrow(() -> new RuntimeException("Problem not found with id: " + request.getProblemId()));

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + request.getUserId()));


        Ide ide = new Ide();
        ide.setUser(user);
        ide.setProblem(problem);
        ide.setUsercode(request.getUsercode());

        Ide savedIde = ideRepository.save(ide);

        return IdeResponse.from(savedIde);
    }

//    public IdeCompilerResponse getOutput(Long ideId) {
//        Ide ide = ideRepository.findById(ideId)
//                .orElseThrow(() -> new RuntimeException("Ide not found with id: " + ideId));
//
//        Problem problem = ide.getProblem();
//        // 가정: ProblemTestCase가 하나만 존재한다.
//        ProblemTestCase testCase = problem.getProblemTestCases().get(0);
//
//        // 컴파일 결과
//        String actualOutput = IdeCompilerService.compile(ide.getUsercode());
//
//        // 출력 예제와 실제 출력을 비교
//        boolean solved = actualOutput.equals(testCase.getOutput());
//
//        ide.setState(ide.isState());
//        ideRepository.save(ide); // 결과 업데이트
//
//        return IdeCompilerResponse.from(ide);
//    }
}
