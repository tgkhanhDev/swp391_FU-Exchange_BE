package com.adkp.fuexchange.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor(force = true)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "ChatMessage")
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int chatMessageId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "chatRoomId", referencedColumnName = "chatRoomId")
    private ChatRoom chatRoomId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "registeredStudentId", referencedColumnName = "registeredStudentId", insertable = false, updatable = false)
    private RegisteredStudent studentSendId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "registeredStudentId", referencedColumnName = "registeredStudentId", insertable = false, updatable = false)
    private RegisteredStudent studentReceiveId;

    private String content;

    private LocalDateTime timeSend;

    public ChatMessage(ChatRoom chatRoomId, RegisteredStudent studentSendId, RegisteredStudent studentReceiveId, String content, LocalDateTime timeSend) {
        this.chatRoomId = chatRoomId;
        this.studentSendId = studentSendId;
        this.studentReceiveId = studentReceiveId;
        this.content = content;
        this.timeSend = timeSend;
    }
}
