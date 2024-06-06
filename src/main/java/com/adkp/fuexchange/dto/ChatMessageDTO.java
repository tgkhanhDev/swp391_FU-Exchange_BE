package com.adkp.fuexchange.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChatMessageDTO {

    private int chatMessageId;

    private int chatRoom;

    private int studentSendId;

    private int studentReceiveId;

    private String content;

    private LocalDateTime timeSend;
}
