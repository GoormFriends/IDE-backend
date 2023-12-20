package com.goorm.goormfriends.api.controller;

import com.goorm.goormfriends.api.dto.response.ProblemResponse;
import com.goorm.goormfriends.api.service.ProblemService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Transactional
@RestController
@RequestMapping("/api/problems")
@RequiredArgsConstructor
public class ProblemController {

    private final ProblemService problemService;

    // 사용자 ID에 따라 문제 목록을 조회하는 엔드포인트
    @GetMapping("/{userId}")
    public ResponseEntity<List<ProblemResponse>> getProblemsByUserId(@PathVariable("userId") Long userId) {
        List<ProblemResponse> problems = problemService.getProblemsByUserId(userId);
        return ResponseEntity.ok(problems);
    }


}
