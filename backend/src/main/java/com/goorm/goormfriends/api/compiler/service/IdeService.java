package com.goorm.goormfriends.api.compiler.service;

import com.goorm.goormfriends.api.compiler.core.IdeRequest;
import com.goorm.goormfriends.api.compiler.core.IdeResponse;
import com.goorm.goormfriends.api.compiler.service.IdeCompilerService;
import com.goorm.goormfriends.db.entity.Ide;
import com.goorm.goormfriends.db.entity.Problem;
import com.goorm.goormfriends.db.entity.User;
import com.goorm.goormfriends.db.repository.IdeRepository;
import com.goorm.goormfriends.db.repository.ProblemRepository;
import com.goorm.goormfriends.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IdeService {
    private final IdeRepository ideRepository;
    private final ProblemRepository problemRepository;
    private final UserRepository userRepository;
    private final IdeCompilerService ideCompilerService;

    public IdeResponse inputSaveAndCompile(Long userId, Long problemId, IdeRequest request) {
        // 현재 활성 문제 찾기
        Problem problem = problemRepository.findById(problemId)
                .orElseThrow(() -> new RuntimeException("Problem not found with id: " + problemId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

// Ide 엔티티 생성 및 저장
        Ide ide = new Ide();
        ide.setUser(user);
        ide.setProblem(problem);
        ide.setUsercode(request.getUsercode());
        ideRepository.save(ide);

        // 컴파일러 서비스를 통해 코드 실행 및 결과 검증
        ideCompilerService.executeCode(ide.getId());

        //채점결과 반환
        return IdeResponse.from(ide);
    }

}
