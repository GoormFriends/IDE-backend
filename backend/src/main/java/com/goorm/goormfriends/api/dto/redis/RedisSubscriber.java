package com.goorm.goormfriends.api.dto.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goorm.goormfriends.db.entity.ChatMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class RedisSubscriber implements MessageListener {
    private ObjectMapper objectMapper;
    private RedisTemplate redisTemplate;
    private SimpMessageSendingOperations messageSendingOperations;

    public RedisSubscriber(@Qualifier("redisTemplate") RedisTemplate<String, ChatMessage> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        log.info("구독");
        try {
            // redis에서 발행된 데이터를 받아 deserialize
            String publishMessage = (String) redisTemplate.getStringSerializer().deserialize(message.getBody());
            // ChatMessage 객채로 맵핑
            ChatMessage roomMessage = objectMapper.readValue(publishMessage, ChatMessage.class);
            // Websocket 구독자에게 채팅 메시지 Send
            messageSendingOperations.convertAndSend("/sub/chat/" + roomMessage.getSenderId() + "/" + roomMessage.getProblemId()
                    , roomMessage);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}

