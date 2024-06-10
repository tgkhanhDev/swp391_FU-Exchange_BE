package com.adkp.fuexchange.service;

import com.adkp.fuexchange.dto.ChatRoomDTO;
import com.adkp.fuexchange.mapper.ChatRoomMapper;
import com.adkp.fuexchange.pojo.ChatMessage;
import com.adkp.fuexchange.pojo.ChatRoom;
import com.adkp.fuexchange.pojo.RegisteredStudent;
import com.adkp.fuexchange.repository.ChatMessageRepository;
import com.adkp.fuexchange.repository.ChatRoomRepository;
import com.adkp.fuexchange.repository.RegisteredStudentRepository;
import com.adkp.fuexchange.request.ChatRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {


    private final ChatRoomRepository chatRoomRepository;

    private final ChatMessageRepository chatMessageRepository;

    private final ChatRoomMapper chatRoomMapper;

    private final RegisteredStudentRepository registeredStudentRepository;


    @Autowired
    public ChatServiceImpl(ChatRoomRepository chatRoomRepository, ChatMessageRepository chatMessageRepository, ChatRoomMapper chatRoomMapper, RegisteredStudentRepository registeredStudentRepository) {
        this.chatRoomRepository = chatRoomRepository;
        this.chatMessageRepository = chatMessageRepository;
        this.chatRoomMapper = chatRoomMapper;
        this.registeredStudentRepository = registeredStudentRepository;
    }

    public List<ChatRoomDTO> getChatRoomByRegisteredStudentId(Integer registeredStudentId) {

        return chatRoomMapper.toChatRoomDTOList(chatRoomRepository.getChatRoomByRegisteredStudentId(registeredStudentId));
    }

    @Override
    @Transactional
    public ChatMessage sendChatMessage(ChatRequest chatRequest) {

        return chatMessageRepository.save(
                ChatMessage.builder()
                        .chatRoomId(saveChatRoom(chatRequest.getChatRoomId()))
                        .studentSendId(getRegisteredStudent(chatRequest.getStudentSendId()))
                        .studentReceiveId(getRegisteredStudent(chatRequest.getStudentReceiveId()))
                        .content(chatRequest.getContent())
                        .timeSend(LocalDateTime.now())
                        .build());
    }

    private ChatRoom saveChatRoom(Integer chatRoomId) {
        if (chatRoomRepository.existsById(chatRoomId)) {
            return chatRoomRepository.save(chatRoomRepository.getReferenceById(chatRoomId));
        }
        return chatRoomRepository.save(new ChatRoom(true));
    }

    private RegisteredStudent getRegisteredStudent(Integer registeredStudentId) {
        return registeredStudentRepository.getReferenceById(registeredStudentId);
    }
}
