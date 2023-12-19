package com.goorm.goormfriends.api.controller;

import com.goorm.goormfriends.api.dto.request.IdeRequest;
import com.goorm.goormfriends.api.dto.response.IdeCompilerResponse;
import com.goorm.goormfriends.api.dto.response.IdeResponse;
import com.goorm.goormfriends.api.dto.response.WrapperResponse;
import com.goorm.goormfriends.api.service.IdeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/solve")
public class IdeController {

        private final IdeService ideService;

        //코드 입력
        @PostMapping("/{userId}/{problemId}")
        public ResponseEntity<WrapperResponse<IdeResponse>> addInput(@PathVariable Long userId,
                                                                     @PathVariable String problemId,
                                                                     @RequestBody IdeRequest ideRequest) {
                IdeResponse ideResponse = ideService.addInput(userId, problemId, ideRequest);
                WrapperResponse<IdeResponse> wrapperResponse = new WrapperResponse<>(ideResponse);
                // 성공적인 처리 후 200 OK 상태 코드와 함께 응답 반환
                return new ResponseEntity<>(wrapperResponse, HttpStatus.OK);
        }

        // 결과 출력
//        @GetMapping("/{userId}/{problemId}")
//        public ResponseEntity<WrapperResponse<IdeCompilerResponse>> getOutput(@PathVariable Long userId, @PathVariable String problemId) {
//                IdeCompilerResponse compilerResponse = ideService.getOutput(userId, problemId);
//                WrapperResponse<IdeCompilerResponse> wrapperResponse = new WrapperResponse<>(compilerResponse);
//                return ResponseEntity.ok(wrapperResponse);
//        }

}
