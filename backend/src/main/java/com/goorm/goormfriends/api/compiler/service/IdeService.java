package com.goorm.goormfriends.api.compiler.service;

import com.goorm.goormfriends.api.compiler.core.IdeRequest;
import com.goorm.goormfriends.api.compiler.core.IdeResponse;
import com.goorm.goormfriends.db.entity.Ide;
import com.goorm.goormfriends.db.entity.Problem;
import com.goorm.goormfriends.db.entity.User;
import com.goorm.goormfriends.db.repository.IdeRepository;
import com.goorm.goormfriends.db.repository.ProblemRepository;
import com.goorm.goormfriends.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IdeService {
    private final IdeRepository ideRepository;
    private final ProblemRepository problemRepository;
    private final UserRepository userRepository;
    private final IdeCompilerService ideCompilerService;

    @Transactional
    public IdeResponse inputSaveAndCompile(Long userId, Long problemId, IdeRequest request) {
        Problem problem = problemRepository.findById(problemId)
                .orElseThrow(() -> new RuntimeException("Problem not found with id: " + problemId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        // Ide 엔티티 확인 및 생성/업데이트
        Optional<Ide> existingIde = ideRepository.findByUserIdAndProblemId(userId, problemId);
        Ide ide;
        if (existingIde.isPresent()) {
            // 기존 데이터 업데이트
            ide = existingIde.get();
            updateIdeData(ide, request.getUsercode());
        } else {
            // 새로운 Ide 생성
            ide = createNewIde(user, problem, request.getUsercode());
            ideRepository.save(ide);
        }

        // 컴파일러 서비스를 통해 코드 실행 및 결과 검증
        ideCompilerService.executeCode(ide.getId());

        // 채점 결과 반환
        return IdeResponse.from(ide);
    }

    private void updateIdeData(Ide ide, String userCode) {
        String defaultUserCode = "import java.util.*;\n" +
                "\n" +
                "public class Main {\n" +
                "    public static void main(String[] args){\n" +
                "        \n" +
                "    }\n" +
                "}"; // 필요에 따라 변경
        ide.setUsercode(userCode != null && !userCode.isEmpty() ? userCode : defaultUserCode);
    }

    private Ide createNewIde(User user, Problem problem, String userCode) {
        Ide ide = new Ide();
        ide.setUser(user);
        ide.setProblem(problem);
        updateIdeData(ide, userCode);
        return ide;
    }
}