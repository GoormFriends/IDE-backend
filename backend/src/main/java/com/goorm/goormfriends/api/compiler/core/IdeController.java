package com.goorm.goormfriends.api.compiler.core;

import com.goorm.goormfriends.api.compiler.service.IdeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/solve")
public class IdeController {
        private final IdeService ideService;

        // 코드 입력 및 채점 결과 저장
        @PostMapping("/{userId}/{problemId}")
        public ResponseEntity<WrapperResponse<IdeResponse>> inputSaveAndCompile(@PathVariable Long userId,
                                                                                @PathVariable Long problemId,
                                                                                @RequestBody IdeRequest ideRequest) {
                // 코드 저장 및 컴파일 실행
                IdeResponse ideResponse = ideService.inputSaveAndCompile(userId, problemId, ideRequest);

                // 결과물 wrapperResponse로 감싸 반환
                WrapperResponse<IdeResponse> wrapperResponse = new WrapperResponse<>(ideResponse);
                return new ResponseEntity<>(wrapperResponse, HttpStatus.OK);
        }
}
