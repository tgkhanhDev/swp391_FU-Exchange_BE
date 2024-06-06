package com.adkp.fuexchange.mapper;

import com.adkp.fuexchange.dto.ChatMessageDTO;
import com.adkp.fuexchange.pojo.ChatMessage;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ChatMessageMapper {
    @Mapping(source = "chatMessageId", target = "chatMessageId")
    @Mapping(source = "chatRoomId.chatRoomId", target = "chatRoom")
    @Mapping(source = "studentSendId.registeredStudentId", target = "studentSendId")
    @Mapping(source = "studentReceiveId.registeredStudentId", target = "studentReceiveId")
    @Mapping(source = "content", target = "content")
    @Mapping(source = "timeSend", target = "timeSend")
    ChatMessageDTO toChatMessageDTO(ChatMessage chatMessage);

    @InheritInverseConfiguration(name = "toChatMessageDTO")
    ChatMessage toChatMessage(ChatMessageDTO chatMessageDTO);

    List<ChatMessageDTO> toChatMessageDTOList(List<ChatMessage> chatMessageList);

    List<ChatMessage> toChatMessageList(List<ChatMessageDTO> chatMessageDTOList);
}
