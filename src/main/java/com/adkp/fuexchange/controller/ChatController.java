package com.adkp.fuexchange.controller;

import com.adkp.fuexchange.repository.ChatRoomRepository;
import com.adkp.fuexchange.response.ResponseObject;
import com.adkp.fuexchange.service.ChatService;
import com.adkp.fuexchange.service.ChatServiceImpl;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chat")
@Tag(name = "Chat")
public class ChatController {

    private final ChatService chatService;

    @Autowired
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/{registeredStudentId}")
    public ResponseObject<Object> getChatRoomByRegisteredStudentId(@PathVariable int registeredStudentId) {
        if (registeredStudentId != 0) {
            return chatService.getChatRoomByRegisteredStudentId(registeredStudentId);
        }
        return ResponseObject.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(HttpStatus.BAD_REQUEST.name())
                .content("Vui lòng nhập đầy đủ thông tin!")
                .build();
    }

    @GetMapping("/get-chat-message")
    @Hidden
    public ResponseObject<Object> getChatMessageByChatRoomId(@RequestParam Integer chatRoomId) {

        return null;

    }
}
