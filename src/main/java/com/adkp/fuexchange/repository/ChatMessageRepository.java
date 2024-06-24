package com.adkp.fuexchange.repository;

import com.adkp.fuexchange.pojo.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, ChatMessage> {

    @Query("Select cms From ChatMessage cms Where cms.chatRoomId.chatRoomId = :chatRoomId")
    List<ChatMessage> getChatMessageByChatRoomId(@Param("chatRoomId") Integer chatRoomId);
}
