package com.adkp.fuexchange.repository;

import com.adkp.fuexchange.pojo.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Integer> {

    @Query(value = "Select DISTINCT cr.* from ChatRoom cr Join ChatMessage cms " +
            "On cr.chatRoomId = cms.chatRoomId " +
            "Where cms.studentSendId = :registeredStudentId or cms.studentReceiveId = :registeredStudentId " +
            "Order By cms.timeSend ASC",
            nativeQuery = true
    )
    List<ChatRoom> getChatRoomByRegisteredStudentId(@Param("registeredStudentId") Integer registeredStudentId);
}
