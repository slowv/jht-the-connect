package com.vfin.theconnect.service;

import com.vfin.theconnect.service.dto.ImageDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.vfin.theconnect.domain.Image}.
 */
public interface ImageService {

    /**
     * Save a image.
     *
     * @param imageDTO the entity to save.
     * @return the persisted entity.
     */
    ImageDTO save(ImageDTO imageDTO);

    /**
     * Get all the images.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ImageDTO> findAll(Pageable pageable);
    /**
     * Get all the ImageDTO where AccountInfo is {@code null}.
     *
     * @return the list of entities.
     */
    List<ImageDTO> findAllWhereAccountInfoIsNull();


    /**
     * Get the "id" image.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ImageDTO> findOne(String id);

    /**
     * Delete the "id" image.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
