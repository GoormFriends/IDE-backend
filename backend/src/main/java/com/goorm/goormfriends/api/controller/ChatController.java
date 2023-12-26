package com.goorm.goormfriends.api.controller;

import com.goorm.goormfriends.api.dto.redis.RedisPublisher;
import com.goorm.goormfriends.api.dto.request.ChatMessageRequest;
import com.goorm.goormfriends.api.service.ChatService;
import com.goorm.goormfriends.db.entity.ChatMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ChatController {
    private final RedisPublisher redisPublisher;
    private final ChatService chatService;

    //@MessageMapping("/chat")
//    public void message(@Payload ChatMessageRequest chatMessageRequest) {
//        try {
//            ChatMessage chatMessage = chatService.saveMessage(chatMessageRequest);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }

    // 메세지 불러오기
//    @GetMapping("/chat/{userId}/{problemId}")
//    public List<ChatMessage> loadMessage(@PathVariable("userId") String userId,
//                                         @PathVariable("problemId") String problemId) {
//        return chatService.loadMessage(userId, problemId);
//    }
}
