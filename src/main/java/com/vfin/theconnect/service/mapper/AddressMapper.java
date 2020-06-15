package com.vfin.theconnect.service.mapper;

import com.vfin.theconnect.domain.*;
import com.vfin.theconnect.service.dto.AddressDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Address} and its DTO {@link AddressDTO}.
 */
@Mapper(componentModel = "spring", uses = {DistrictMapper.class, AccountInfoMapper.class})
public interface AddressMapper extends EntityMapper<AddressDTO, Address> {

    @Mapping(source = "district.id", target = "districtId")
    @Mapping(source = "accountInfo.id", target = "accountInfoId")
    AddressDTO toDto(Address address);

    @Mapping(source = "districtId", target = "district")
    @Mapping(source = "accountInfoId", target = "accountInfo")
    Address toEntity(AddressDTO addressDTO);

    default Address fromId(String id) {
        if (id == null) {
            return null;
        }
        Address address = new Address();
        address.setId(id);
        return address;
    }
}
