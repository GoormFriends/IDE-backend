package com.goorm.goormfriends.api.service;

import com.goorm.goormfriends.api.dto.request.ChatMessageRequest;
import com.goorm.goormfriends.db.entity.ChatMessage;
import com.goorm.goormfriends.db.entity.User;
import com.goorm.goormfriends.db.repository.ChatMessageRepository;
import com.goorm.goormfriends.db.repository.ProblemRepository;
import com.goorm.goormfriends.db.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ChatService {

    private final ChatMessageRepository chatMessageRepository;
    private final UserRepository userRepository;
    private final ProblemRepository problemRepository;
    private final RedisTemplate<String, ChatMessage> redisTemplate;

    public ChatMessage saveMessage(ChatMessageRequest chatMessageRequest)
            throws Exception {

        ChatMessage chatMessage = new ChatMessage(chatMessageRequest);
        log.info(chatMessageRequest.getMessage() + " 메시지 전송");

        chatMessageRepository.save(chatMessage);
        redisTemplate.opsForList().leftPush(generateRoomId(chatMessageRequest.getOwnerId(), chatMessageRequest.getProblemId()), chatMessage);
        redisTemplate.expire(generateRoomId(chatMessageRequest.getOwnerId(), chatMessageRequest.getProblemId()), 1, TimeUnit.DAYS);

        return chatMessage;
    }

    public List<ChatMessage> loadMessage(String ownerId, String problemId) {
        String roomId = generateRoomId(ownerId, problemId);
        List<ChatMessage> messageList = new ArrayList<>();

        List<ChatMessage> redisMessageList = redisTemplate.opsForList().range(roomId, 0, -1);
        if (redisMessageList == null || redisMessageList.isEmpty()) {
            log.info("bring by db");
            List<ChatMessage> dbMessageList = chatMessageRepository.findByOwnerIdAndProblemId(Long.valueOf(ownerId), Long.valueOf(problemId));
            messageList.addAll(dbMessageList);
        } else {
            messageList.addAll(redisMessageList);
        }
        return messageList;
    }

    private String generateRoomId(String userId, String problemId) {
        // Implement logic to generate a unique room ID based on userId and problemId
        return userId + "_" + problemId;
    }
}
