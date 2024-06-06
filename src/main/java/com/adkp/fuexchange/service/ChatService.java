package com.adkp.fuexchange.service;

import com.adkp.fuexchange.response.ResponseObject;

public interface ChatService {

    ResponseObject<Object> getChatMessageByChatRoomId(String chatRoomId);

    ResponseObject<Object> getChatRoomByRegisteredStudentId(Integer registeredStudentId);
}
