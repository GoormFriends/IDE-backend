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
//        if (!problemRepository.existsById(chatMessageRequest.getProblemId())) {
//            throw new Exception("Can't find Problem");
//        } else if (!userRepository.existsById(chatMessageRequest.getOwnerId())) {
//            throw new Exception("Can't find owner");
//        }
        //Problem problem = problemRepository.findById(problemId).orElseThrow(() -> new Exception("Can't find Problem"));
        //User owner = userRepository.findById(ownerId).orElseThrow(() -> new Exception("Can't find Owner"));
        User user = userRepository.findById(chatMessageRequest.getUserId()).orElseThrow(() -> new Exception("Can't find user"));

        ChatMessage chatMessage = new ChatMessage(chatMessageRequest);
//        if (chatMessageRequest.getMessageType().equals(0)) {
//            chatMessage.setMessage(user.getNickname() + "님이 입장하셨습니다.");
//            log.info("입장 메세지 전송");
//        } else if (chatMessageRequest.getMessageType().equals(2)) {
//            chatMessage.setMessage(user.getNickname() + "님이 퇴장하셨습니다.");
//            log.info("퇴장 메세지 전송");
//        } else {
//            log.info(chatMessageRequest.getMessage() + " 메시지 전송");
//        }

        chatMessageRepository.save(chatMessage);
        redisTemplate.opsForList().leftPush(generateRoomId(chatMessageRequest.getUserId(), chatMessageRequest.getProblemId()), chatMessage);
        redisTemplate.expire(generateRoomId(chatMessageRequest.getUserId(), chatMessageRequest.getProblemId()), 1, TimeUnit.DAYS);

        return chatMessage;
    }

    public List<ChatMessage> loadMessage(Long ownerId, Long problemId) {
        String roomId = generateRoomId(ownerId, problemId);
        List<ChatMessage> messageList = new ArrayList<>();

        List<ChatMessage> redisMessageList = redisTemplate.opsForList().range(roomId, 0, -1);
        if (redisMessageList == null || redisMessageList.isEmpty()) {
            log.info("bring by db");
            List<ChatMessage> dbMessageList = chatMessageRepository.findByOwnerIdAndProblemId(ownerId, problemId);
            messageList.addAll(dbMessageList);
        } else {
            messageList.addAll(redisMessageList);
        }
        return messageList;
    }

    private String generateRoomId(Long userId, Long problemId) {
        // Implement logic to generate a unique room ID based on userId and problemId
        return String.valueOf(userId) + "_" + String.valueOf(problemId);
    }
}
