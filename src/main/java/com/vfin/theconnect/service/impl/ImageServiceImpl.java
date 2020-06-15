package com.vfin.theconnect.service.impl;

import com.vfin.theconnect.service.ImageService;
import com.vfin.theconnect.domain.Image;
import com.vfin.theconnect.repository.ImageRepository;
import com.vfin.theconnect.service.dto.ImageDTO;
import com.vfin.theconnect.service.mapper.ImageMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing {@link Image}.
 */
@Service
public class ImageServiceImpl implements ImageService {

    private final Logger log = LoggerFactory.getLogger(ImageServiceImpl.class);

    private final ImageRepository imageRepository;

    private final ImageMapper imageMapper;

    public ImageServiceImpl(ImageRepository imageRepository, ImageMapper imageMapper) {
        this.imageRepository = imageRepository;
        this.imageMapper = imageMapper;
    }

    /**
     * Save a image.
     *
     * @param imageDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ImageDTO save(ImageDTO imageDTO) {
        log.debug("Request to save Image : {}", imageDTO);
        Image image = imageMapper.toEntity(imageDTO);
        image = imageRepository.save(image);
        return imageMapper.toDto(image);
    }

    /**
     * Get all the images.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    public Page<ImageDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Images");
        return imageRepository.findAll(pageable)
            .map(imageMapper::toDto);
    }



    /**
    *  Get all the images where AccountInfo is {@code null}.
     *  @return the list of entities.
     */
    public List<ImageDTO> findAllWhereAccountInfoIsNull() {
        log.debug("Request to get all images where AccountInfo is null");
        return StreamSupport
            .stream(imageRepository.findAll().spliterator(), false)
            .filter(image -> image.getAccountInfo() == null)
            .map(imageMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one image by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<ImageDTO> findOne(String id) {
        log.debug("Request to get Image : {}", id);
        return imageRepository.findById(id)
            .map(imageMapper::toDto);
    }

    /**
     * Delete the image by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Image : {}", id);
        imageRepository.deleteById(id);
    }
}
