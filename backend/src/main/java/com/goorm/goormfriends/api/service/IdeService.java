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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IdeService {
    private final IdeRepository ideRepository;
    private final ProblemRepository problemRepository;
    private final UserRepository userRepository;
    private final IdeCompilerService ideCompilerService;


    public IdeResponse addInput(IdeRequest request) {
        Optional<Problem> problem = problemRepository.findById(request.getProblem().getId());
        if (!problem.isPresent()) {
            throw new RuntimeException("Problem not found with id: " + request.getProblem().getId());
        }

        Optional<User> user = userRepository.findById(request.getUser().getId());
        if (!user.isPresent()) {
            throw new RuntimeException("User not found with id: " + request.getUser().getId());
        }

        Ide ide = new Ide();
        ide.setProblem(problem.get());
        ide.setUsercode(request.getUsercode());
        ide.setUser(user.get());

        Ide savedIde = ideRepository.save(ide);
        ProblemTestCase testCase = problem.get().getProblemTestCases().get(0); // 첫 번째 테스트 케이스를 가져옴

        return IdeResponse.from(savedIde, testCase);
    }

    public IdeCompilerResponse getOutput(Long ideId) {
        Ide ide = ideRepository.findById(ideId)
                .orElseThrow(() -> new RuntimeException("Ide not found with id: " + ideId));

        Problem problem = ide.getProblem();
        // 가정: ProblemTestCase가 하나만 존재한다.
        ProblemTestCase testCase = problem.getProblemTestCases().get(0);

        // 컴파일 결과
        String actualOutput = IdeCompilerService.compile(ide.getUsercode());

        // 출력 예제와 실제 출력을 비교
        boolean solved = actualOutput.equals(testCase.getOutput());

        ide.setSolved(solved);
        ideRepository.save(ide); // 결과 업데이트

        return IdeCompilerResponse.from(ide);
    }
}
