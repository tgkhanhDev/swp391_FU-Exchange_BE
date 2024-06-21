package com.adkp.fuexchange.repository;

import com.adkp.fuexchange.pojo.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Integer> {

    @Query(value = "SELECT DISTINCT cr.* FROM ChatRoom cr " +
            "JOIN ChatMessage cms ON cr.chatRoomId = cms.chatRoomId " +
            "WHERE cms.studentSendId = :registeredStudentId OR cms.studentReceiveId = :registeredStudentId",
            nativeQuery = true)
    List<ChatRoom> getChatRoomByRegisteredStudentId(@Param("registeredStudentId") Integer registeredStudentId);
}
