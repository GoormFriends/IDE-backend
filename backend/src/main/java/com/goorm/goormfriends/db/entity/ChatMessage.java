package com.goorm.goormfriends.db.entity;

import com.goorm.goormfriends.api.dto.request.ChatMessageRequest;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Table(name="chatMessage")
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatMessage{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Long id;

    private Long ownerId;

    private Long problemId;

    private String userId;

    private String message;

    private String userNickname;
    private String userProfile;
    private String time;

    public ChatMessage(ChatMessageRequest messageRequest) {
        this.userId = messageRequest.getUserId();
        this.ownerId = Long.valueOf(messageRequest.getOwnerId());
        this.problemId = Long.valueOf(messageRequest.getProblemId());
        this.message = messageRequest.getMessage();
        this.time = messageRequest.getTime();
        this.userNickname = messageRequest.getUserNickname();
        this.userProfile = messageRequest.getUserProfile();

    }

}
