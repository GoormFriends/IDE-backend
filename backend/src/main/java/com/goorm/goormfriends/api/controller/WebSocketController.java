package com.goorm.goormfriends.api.controller;

import com.goorm.goormfriends.api.dto.request.ChatMessageRequest;
import com.goorm.goormfriends.api.service.ChatService;
import com.goorm.goormfriends.db.entity.ChatMessage;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class WebSocketController {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ChatService chatService;

    @MessageMapping("/chat")
    public void sendMessage(ChatMessageRequest chatMessageRequest
                            ,SimpMessageHeaderAccessor accessor
    ) throws Exception {
        ChatMessage chatMessage = chatService.saveMessage(chatMessageRequest);
        simpMessagingTemplate.convertAndSend("/sub/chat/" + chatMessageRequest.getOwnerId() + "/" + chatMessageRequest.getProblemId(),
                chatMessageRequest);
    }
    @GetMapping("/chat/{ownerId}/{problemId}")
    public List<ChatMessage> loadMessage(@Nullable @AuthenticationPrincipal User user,
                                         @PathVariable("ownerId") String ownerId, @PathVariable("problemId") String problemId) {
        System.out.println("check-loadMessage");
        return chatService.loadMessage(ownerId, problemId);
    }

}
