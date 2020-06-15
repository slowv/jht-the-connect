package com.vfin.theconnect.service.mapper;

import com.vfin.theconnect.domain.*;
import com.vfin.theconnect.service.dto.UserInfoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserInfo} and its DTO {@link UserInfoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UserInfoMapper extends EntityMapper<UserInfoDTO, UserInfo> {



    default UserInfo fromId(String id) {
        if (id == null) {
            return null;
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setId(id);
        return userInfo;
    }
}
