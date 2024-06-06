package com.adkp.fuexchange.mapper;

import com.adkp.fuexchange.dto.ChatMessageDTO;
import com.adkp.fuexchange.dto.ChatRoomDTO;
import com.adkp.fuexchange.pojo.ChatMessage;
import com.adkp.fuexchange.pojo.ChatRoom;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {ChatMessageMapper.class}
)
public interface ChatRoomMapper {

    @Mapping(source = "chatRoomId", target = "chatRoomId")
    @Mapping(source = "isActive", target = "isActive")
    @Mapping(source = "chatMessageId", target = "chatMessage")
    ChatRoomDTO toChatRoomDTO(ChatRoom chatRoom);

    @InheritInverseConfiguration(name = "toChatRoomDTO")
    ChatRoom toChatRoom(ChatRoomDTO chatRoomDTO);

    List<ChatRoomDTO> totoChatRoomDTOList(List<ChatRoom> chatMessageList);

    List<ChatRoom> totoChatRoomList(List<ChatRoomDTO> chatMessageDTOList);

}
