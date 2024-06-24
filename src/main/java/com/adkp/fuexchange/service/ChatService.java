package com.adkp.fuexchange.service;

import com.adkp.fuexchange.dto.ChatRoomDTO;
import com.adkp.fuexchange.pojo.ChatMessage;
import com.adkp.fuexchange.request.ChatRequest;

import java.util.List;

public interface ChatService {

    List<ChatRoomDTO> getChatRoomByRegisteredStudentId(Integer registeredStudentId);

    ChatMessage sendChatMessage(ChatRequest chatRequest);
}
