package com.goorm.goormfriends.api.controller;

import com.goorm.goormfriends.api.dto.request.ChatMessageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class WebSocketController {
    private final SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/chat")
    public void sendMessage(ChatMessageRequest chatMessageRequest
//                            ,SimpMessageHeaderAccessor accessor
    ) {
        simpMessagingTemplate.convertAndSend("/sub/chat/" + chatMessageRequest.getOwnerId() + "/" + chatMessageRequest.getProblemId(),
                chatMessageRequest);
    }
}
