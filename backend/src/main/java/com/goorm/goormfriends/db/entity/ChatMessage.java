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

//    @ManyToOne(targetEntity = Problem.class, fetch = LAZY)
//    @JoinColumn(name = "problem_id")
//    private Problem problem;

    private Long problemId;

//    @ManyToOne(targetEntity = ChatRoom.class, fetch = LAZY)
//    @JoinColumn(name = "rood_id")
//    private ChatRoom room;

    @ManyToOne(targetEntity = User.class, fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String message;

    private Integer messageType;

    //    @CreatedDate
    private String createdDate;

    public ChatMessage(ChatMessageRequest messageRequest, User user, Long ownerId, Long problemId) {
        this.user = user;
        this.ownerId = ownerId;
        this.problemId = problemId;
        this.message = messageRequest.getMessage();
        this.messageType = messageRequest.getMessageType();
        this.createdDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yy-MM-dd HH:mm:ss"));
    }

//    public ChatMessage(String message, User user, ChatRoom chatRoom) {
//        this.message = message;
//        this.user = user;
//        this.room = chatRoom;
//    }
}
