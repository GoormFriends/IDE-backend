package com.goorm.goormfriends.api.dto.redis;

import com.goorm.goormfriends.db.entity.ChatMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class RedisPublisher {

    private final RedisTemplate<String, ChatMessage> redisTemplate;

    public void publish(ChannelTopic topic, ChatMessage message) {
        log.info("publish");
        redisTemplate.convertAndSend(topic.getTopic(), message);
    }

}
