package com.adkp.fuexchange.service;

import com.adkp.fuexchange.dto.ChatMessageDTO;
import com.adkp.fuexchange.dto.ChatRoomDTO;
import com.adkp.fuexchange.pojo.ChatMessage;
import com.adkp.fuexchange.request.ChatRequest;
import com.adkp.fuexchange.request.ContactToSellerRequest;

import java.util.List;

public interface ChatService {

    List<ChatRoomDTO> getChatRoomByRegisteredStudentId(Integer registeredStudentId);

    ChatMessageDTO sendChatMessage(ChatRequest chatRequest);

    ChatRoomDTO getChatRoomByRegisteredStudentIdAndSellerId(Integer registeredStudentId, Integer sellerId);

    ChatMessageDTO contactToSeller(ContactToSellerRequest contactToSellerRequest);
}
