package com.adkp.fuexchange.controller;

import com.adkp.fuexchange.dto.ChatMessageDTO;
import com.adkp.fuexchange.pojo.ChatMessage;
import com.adkp.fuexchange.request.ChatRequest;
import com.adkp.fuexchange.response.ResponseObject;
import com.adkp.fuexchange.service.ChatService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chat")
@Tag(name = "Chat")
@Validated
public class ChatController {

    private final ChatService chatService;

    @Autowired
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/chat-room/{registeredStudentId}")
    public ResponseObject<Object> getChatRoomByRegisteredStudentId(@PathVariable int registeredStudentId) {

        if (registeredStudentId != 0) {
            return ResponseObject.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message(HttpStatus.BAD_REQUEST.name())
                    .content("Xem thông tin thành công!")
                    .data(chatService.getChatRoomByRegisteredStudentId(registeredStudentId))
                    .build();
        }
        return ResponseObject.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(HttpStatus.BAD_REQUEST.name())
                .content("Vui lòng nhập đầy đủ thông tin!")
                .build();
    }

    @PostMapping("/send-message")
    public ResponseObject<Object> sendMessage(@RequestBody @Valid ChatRequest chatRequest) {
        ChatMessage chatMessage = chatService.sendChatMessage(chatRequest);
        return ResponseObject.builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.name())
                .content("Gửi tin nhắn thành công!")
                .data(
                        ChatMessageDTO.builder()
                                .chatMessageId(chatMessage.getChatMessageId())
                                .chatRoom(chatMessage.getChatRoomId().getChatRoomId())
                                .studentSendId(chatMessage.getStudentSendId().getRegisteredStudentId())
                                .studentReceiveId(chatMessage.getStudentReceiveId().getRegisteredStudentId())
                                .content(chatMessage.getContent())
                                .timeSend(chatMessage.getTimeSend())
                                .build()
                )
                .build();
    }
}
