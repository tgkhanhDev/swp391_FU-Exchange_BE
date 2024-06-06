package com.adkp.fuexchange.service;

import com.adkp.fuexchange.mapper.ChatRoomMapper;
import com.adkp.fuexchange.repository.ChatMessageRepository;
import com.adkp.fuexchange.repository.ChatRoomRepository;
import com.adkp.fuexchange.response.ResponseObject;
import com.adkp.fuexchange.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ChatServiceImpl implements ChatService {

    private final Utils utils;

    private final ChatRoomRepository chatRoomRepository;

    private final ChatRoomMapper chatRoomMapper;


    @Autowired
    public ChatServiceImpl(Utils utils, ChatRoomRepository chatRoomRepository, ChatRoomMapper chatRoomMapper) {
        this.utils = utils;
        this.chatRoomRepository = chatRoomRepository;
        this.chatRoomMapper = chatRoomMapper;
    }

    public ResponseObject<Object> getChatRoomByRegisteredStudentId(Integer registeredStudentId) {
//        String data = null;
//        utils.navigationDataAsyncForGetMethod("http://localhost:8080/chat/get-chat-message", data, HttpMethod.GET);
        return ResponseObject.builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.name())
                .message("Xem thông tin thành công")
               // .data(chatRoomMapper.totoChatRoomDTOList(chatRoomRepository.getChatRoomByRegisteredStudentId(registeredStudentId)))
                .build();
    }

    public ResponseObject<Object> getChatMessageByChatRoomId(String chatRoomId){
        return null;
    }
}
