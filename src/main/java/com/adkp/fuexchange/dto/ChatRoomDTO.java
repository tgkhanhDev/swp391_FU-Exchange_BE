package com.adkp.fuexchange.dto;

import lombok.Data;

import java.util.List;
@Data
public class ChatRoomDTO {

    private int chatRoomId;

    private int isActive;

    private List<ChatMessageDTO> chatMessage;
}
