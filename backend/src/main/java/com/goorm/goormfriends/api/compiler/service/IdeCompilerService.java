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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
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
        Map<String, Object> compileResult = ideCompiler.compile(userCode); //
        if (compileResult.containsKey("error")) {
            log.error("Compile error: {}", compileResult.get("error"));
            ide.setState(State.COMPILE_ERROR);
        } else {
            ide.setState(executeTestCases(ide, compileResult.get("class")));
        }
        ideRepository.save(ide);
    }

    private State executeTestCases(Ide ide, Object compiledClassObj) {
        List<ProblemTestCase> testCases = problemTestCaseRepository.findByProblemId(ide.getProblem().getId());

        if (!(compiledClassObj instanceof Class<?>)) {
            log.error("Compiled class is not a Class<?> instance");
            return State.FAILURE;
        }
        Class<?> compiledClass = (Class<?>) compiledClassObj;

        for (ProblemTestCase testCase : testCases) {
            InputStream originalIn = System.in;
            PrintStream originalOut = System.out;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PrintStream newOut = new PrintStream(baos);

            try {
                // 입력 데이터 로그
                log.trace("Test Case Input: " + testCase.getInput());

                // 표준 입력 리디렉션: 입력 데이터를 바이트 배열로 변환하여 ByteArrayInputStream에 제공
                System.setIn(new ByteArrayInputStream(testCase.getInput().getBytes()));
                System.setOut(newOut);

                // 실행
                Map<String, Object> executionResult = timeOutCall(
                        compiledClass,
                        "main",
                        new Object[]{new String[]{}},
                        new Class[]{String[].class}
                );

                // 출력 결과 캡처
                System.out.flush();
                String userOutput = baos.toString().trim();
                log.trace("User Output: " + userOutput);

                // 기대 출력과 비교
                String expectedOutput = testCase.getOutput().trim();
                log.trace("Expected Output: " + expectedOutput);

                if (!userOutput.equals(expectedOutput)) {
                    log.trace("Result: WRONG_ANSWER");
                    return State.WRONG_ANSWER;
                }
            }catch (Exception e) {
                log.error("Error executing user code: ", e);
                return State.FAILURE;
            } finally {
                // 스트림 복원
                System.setIn(originalIn);
                System.setOut(originalOut);
            }
        }
        log.trace("Result: SUCCESS");
        return State.SUCCESS;



    }

    private Map<String, Object> timeOutCall(Class<?> clazz, String methodName, Object[] params, Class<?>[] arguments) throws Exception {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<Map<String, Object>> task = () -> {
            Map<String, Object> result = new HashMap<>();
            Method method =clazz.getMethod(methodName, arguments);
            result.put("output", method.invoke(null, params));
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

