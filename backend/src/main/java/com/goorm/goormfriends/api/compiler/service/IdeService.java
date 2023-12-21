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
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class IdeService {
    private final IdeRepository ideRepository;
    private final ProblemRepository problemRepository;
    private final UserRepository userRepository;
    private final IdeCompilerService ideCompilerService;

    @Transactional
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
        // usercode에 대한 널 체크와 기본값 설정
        String defaultUserCode = "기본 코드"; // 필요에 따라 변경
        ide.setUsercode(request.getUsercode() != null && !request.getUsercode().isEmpty() ? request.getUsercode() : defaultUserCode);

        ideRepository.save(ide);

        // ide 상태가 null이 아닌지 확인
        if (ide.getState() != null) {
            // 상태가 설정되어 있으면 처리
        }

        // 컴파일러 서비스를 통해 코드 실행 및 결과 검증
        ideCompilerService.executeCode(ide.getId());

        //채점결과 반환
        return IdeResponse.from(ide);
    }

}
