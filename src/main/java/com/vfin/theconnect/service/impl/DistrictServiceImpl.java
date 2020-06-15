package com.vfin.theconnect.service.impl;

import com.vfin.theconnect.service.DistrictService;
import com.vfin.theconnect.domain.District;
import com.vfin.theconnect.repository.DistrictRepository;
import com.vfin.theconnect.service.dto.DistrictDTO;
import com.vfin.theconnect.service.mapper.DistrictMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service Implementation for managing {@link District}.
 */
@Service
public class DistrictServiceImpl implements DistrictService {

    private final Logger log = LoggerFactory.getLogger(DistrictServiceImpl.class);

    private final DistrictRepository districtRepository;

    private final DistrictMapper districtMapper;

    public DistrictServiceImpl(DistrictRepository districtRepository, DistrictMapper districtMapper) {
        this.districtRepository = districtRepository;
        this.districtMapper = districtMapper;
    }

    /**
     * Save a district.
     *
     * @param districtDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public DistrictDTO save(DistrictDTO districtDTO) {
        log.debug("Request to save District : {}", districtDTO);
        District district = districtMapper.toEntity(districtDTO);
        district = districtRepository.save(district);
        return districtMapper.toDto(district);
    }

    /**
     * Get all the districts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    public Page<DistrictDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Districts");
        return districtRepository.findAll(pageable)
            .map(districtMapper::toDto);
    }


    /**
     * Get one district by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<DistrictDTO> findOne(String id) {
        log.debug("Request to get District : {}", id);
        return districtRepository.findById(id)
            .map(districtMapper::toDto);
    }

    /**
     * Delete the district by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete District : {}", id);
        districtRepository.deleteById(id);
    }
}
