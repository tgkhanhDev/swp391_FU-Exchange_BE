package com.adkp.fuexchange.repository;

import com.adkp.fuexchange.pojo.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Integer> {

    @Query("Select cr From ChatRoom cr where cr.chatRoomId = :registeredStudentId And " +
            "cr.chatRoomId = :registeredStudentId")
    List<ChatRoom> getChatRoomByRegisteredStudentId(@Param("registeredStudentId") Integer registeredStudentId);
}
