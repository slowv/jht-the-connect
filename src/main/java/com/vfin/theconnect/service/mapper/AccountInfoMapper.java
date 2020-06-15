package com.vfin.theconnect.service.mapper;

import com.vfin.theconnect.domain.*;
import com.vfin.theconnect.service.dto.AccountInfoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AccountInfo} and its DTO {@link AccountInfoDTO}.
 */
@Mapper(componentModel = "spring", uses = {ImageMapper.class})
public interface AccountInfoMapper extends EntityMapper<AccountInfoDTO, AccountInfo> {

    @Mapping(source = "image.id", target = "imageId")
    AccountInfoDTO toDto(AccountInfo accountInfo);

    @Mapping(source = "imageId", target = "image")
    @Mapping(target = "addresses", ignore = true)
    @Mapping(target = "removeAddress", ignore = true)
    @Mapping(target = "accountTC", ignore = true)
    AccountInfo toEntity(AccountInfoDTO accountInfoDTO);

    default AccountInfo fromId(String id) {
        if (id == null) {
            return null;
        }
        AccountInfo accountInfo = new AccountInfo();
        accountInfo.setId(id);
        return accountInfo;
    }
}
