package com.goorm.goormfriends.db.repository;

import com.goorm.goormfriends.db.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    List<ChatMessage> findBySenderIdAndProblemId(Long senderId, Long problemId);
}
