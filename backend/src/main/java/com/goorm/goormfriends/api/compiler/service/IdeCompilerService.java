package com.goorm.goormfriends.api.compiler.service;

import com.goorm.goormfriends.db.entity.Ide;
import com.goorm.goormfriends.db.entity.ProblemTestCase;
import com.goorm.goormfriends.db.entity.State;
import com.goorm.goormfriends.db.repository.IdeRepository;
import com.goorm.goormfriends.db.repository.ProblemTestCaseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class IdeCompilerService {
    private final IdeRepository ideRepository;
    private final ProblemTestCaseRepository problemTestCaseRepository;
    private final IdeCompiler ideCompiler;
    private static final long TIMEOUT = 15000; // 15초

    @Transactional
    public void executeCode(Long ideId) {
        Ide ide = ideRepository.findById(ideId).orElseThrow(
                () -> new RuntimeException("IDE not found with id: " + ideId));

        String userCode = ide.getUsercode();
        // 컴파일 오류가 있는 경우
        // Map에 에러키 있으면 컴파일 오류로 처리
        Map<String, Object> compileResult = ideCompiler.compile(userCode);
        if (compileResult.containsKey("error")) {
            log.error("Compile error: {}", compileResult.get("error"));
            ide.setState(State.COMPILE_ERROR);
        } else {
            ide.setState(executeTestCases(ide, compileResult.get("class")));
        }
        ideRepository.save(ide);
    }

    private State executeTestCases(Ide ide, Object compiledClass) {
        // 해당 문제의 테스트 케이스들 가져오기
        List<ProblemTestCase> testCases = problemTestCaseRepository.findByProblemId(ide.getProblem().getId());

        // 실제 실행 결과와 테스트 케이스의 예상 출력 비교
        for (ProblemTestCase testCase : testCases) {
            try {
                Map<String, Object> executionResult = timeOutCall(
                        compiledClass,
                        "execute", // 가정: 컴파일된 클래스에 'execute' 메서드가 있음
                        new Object[]{testCase.getInput()},
                        new Class[]{String.class}
                );

                if (!executionResult.get("output").equals(testCase.getOutput())) {
                    return State.WRONG_ANSWER;
                }
            } catch (Exception e) {
                log.error("Error executing user code: {}", e.getMessage());
                return State.FAILURE;
            }
        }
        return State.SUCCESS;

    }

    private Map<String, Object> timeOutCall(Object obj, String methodName, Object[] params, Class<?>[] arguments) throws Exception {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<Map<String, Object>> task = () -> {
            Map<String, Object> result = new HashMap<>();
            Method method = obj.getClass().getMethod(methodName, arguments);
            result.put("output", method.invoke(obj, params));
            return result;
        };

        Future<Map<String, Object>> future = executor.submit(task);
        try {
            return future.get(TIMEOUT, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            log.error("Method execution error: {}", e.getMessage());
            return Map.of("error", "Execution failed or timed out");
        } finally {
            executor.shutdown();
        }
    }
}

