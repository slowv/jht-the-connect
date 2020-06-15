package com.vfin.theconnect.service.mapper;

import com.vfin.theconnect.domain.*;
import com.vfin.theconnect.service.dto.AccountTCDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AccountTC} and its DTO {@link AccountTCDTO}.
 */
@Mapper(componentModel = "spring", uses = {AccountInfoMapper.class})
public interface AccountTCMapper extends EntityMapper<AccountTCDTO, AccountTC> {

    @Mapping(source = "accountInfo.id", target = "accountInfoId")
    AccountTCDTO toDto(AccountTC accountTC);

    @Mapping(source = "accountInfoId", target = "accountInfo")
    AccountTC toEntity(AccountTCDTO accountTCDTO);

    default AccountTC fromId(String id) {
        if (id == null) {
            return null;
        }
        AccountTC accountTC = new AccountTC();
        accountTC.setId(id);
        return accountTC;
    }
}
