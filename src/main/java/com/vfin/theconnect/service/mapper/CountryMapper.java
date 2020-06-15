package com.vfin.theconnect.service.mapper;

import com.vfin.theconnect.domain.*;
import com.vfin.theconnect.service.dto.CountryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Country} and its DTO {@link CountryDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CountryMapper extends EntityMapper<CountryDTO, Country> {


    @Mapping(target = "cities", ignore = true)
    @Mapping(target = "removeCity", ignore = true)
    Country toEntity(CountryDTO countryDTO);

    default Country fromId(String id) {
        if (id == null) {
            return null;
        }
        Country country = new Country();
        country.setId(id);
        return country;
    }
}
