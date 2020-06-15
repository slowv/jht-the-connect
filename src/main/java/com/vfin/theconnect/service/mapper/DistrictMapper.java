package com.vfin.theconnect.service.mapper;

import com.vfin.theconnect.domain.*;
import com.vfin.theconnect.service.dto.DistrictDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link District} and its DTO {@link DistrictDTO}.
 */
@Mapper(componentModel = "spring", uses = {CityMapper.class})
public interface DistrictMapper extends EntityMapper<DistrictDTO, District> {

    @Mapping(source = "city.id", target = "cityId")
    DistrictDTO toDto(District district);

    @Mapping(target = "addresses", ignore = true)
    @Mapping(target = "removeAddress", ignore = true)
    @Mapping(source = "cityId", target = "city")
    District toEntity(DistrictDTO districtDTO);

    default District fromId(String id) {
        if (id == null) {
            return null;
        }
        District district = new District();
        district.setId(id);
        return district;
    }
}
