package com.vfin.theconnect.service.mapper;

import com.vfin.theconnect.domain.*;
import com.vfin.theconnect.service.dto.CityDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link City} and its DTO {@link CityDTO}.
 */
@Mapper(componentModel = "spring", uses = {CountryMapper.class})
public interface CityMapper extends EntityMapper<CityDTO, City> {

    @Mapping(source = "country.id", target = "countryId")
    CityDTO toDto(City city);

    @Mapping(target = "districts", ignore = true)
    @Mapping(target = "removeDistrict", ignore = true)
    @Mapping(source = "countryId", target = "country")
    City toEntity(CityDTO cityDTO);

    default City fromId(String id) {
        if (id == null) {
            return null;
        }
        City city = new City();
        city.setId(id);
        return city;
    }
}
