package com.vfin.theconnect.service.mapper;

import com.vfin.theconnect.domain.*;
import com.vfin.theconnect.service.dto.FriendDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Friend} and its DTO {@link FriendDTO}.
 */
@Mapper(componentModel = "spring", uses = {AccountTCMapper.class})
public interface FriendMapper extends EntityMapper<FriendDTO, Friend> {

    @Mapping(source = "sender.id", target = "senderId")
    @Mapping(source = "receiver.id", target = "receiverId")
    FriendDTO toDto(Friend friend);

    @Mapping(source = "senderId", target = "sender")
    @Mapping(source = "receiverId", target = "receiver")
    Friend toEntity(FriendDTO friendDTO);

    default Friend fromId(String id) {
        if (id == null) {
            return null;
        }
        Friend friend = new Friend();
        friend.setId(id);
        return friend;
    }
}
