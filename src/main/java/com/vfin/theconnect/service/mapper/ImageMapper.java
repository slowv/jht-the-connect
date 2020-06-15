package com.vfin.theconnect.service.mapper;

import com.vfin.theconnect.domain.*;
import com.vfin.theconnect.service.dto.ImageDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Image} and its DTO {@link ImageDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ImageMapper extends EntityMapper<ImageDTO, Image> {


    @Mapping(target = "accountInfo", ignore = true)
    Image toEntity(ImageDTO imageDTO);

    default Image fromId(String id) {
        if (id == null) {
            return null;
        }
        Image image = new Image();
        image.setId(id);
        return image;
    }
}
