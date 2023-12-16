package com.goorm.goormfriends.api.controller;

import com.goorm.goormfriends.api.dto.request.IdeRequest;
import com.goorm.goormfriends.api.dto.response.IdeResponse;
import com.goorm.goormfriends.api.service.IdeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class IdeController {

        private final IdeService ideService;

        //코드 입력
        @PostMapping("/solve")
        public IdeResponse addInput (@RequestBody IdeRequest ideRequest) {

                return ideService.addInput(ideRequest);
        }
/*
        //결과 출력 findById, findByName 조회
        @GetMapping("/solve/{id}")
        public IdeResponse getOutput(@PathVariable Long id) {
            return ideService.findById.getOutput(id);
        }

 */
}
