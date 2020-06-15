package com.vfin.theconnect.service.impl;

import com.vfin.theconnect.service.UserInfoService;
import com.vfin.theconnect.domain.UserInfo;
import com.vfin.theconnect.repository.UserInfoRepository;
import com.vfin.theconnect.service.dto.UserInfoDTO;
import com.vfin.theconnect.service.mapper.UserInfoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service Implementation for managing {@link UserInfo}.
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

    private final Logger log = LoggerFactory.getLogger(UserInfoServiceImpl.class);

    private final UserInfoRepository userInfoRepository;

    private final UserInfoMapper userInfoMapper;

    public UserInfoServiceImpl(UserInfoRepository userInfoRepository, UserInfoMapper userInfoMapper) {
        this.userInfoRepository = userInfoRepository;
        this.userInfoMapper = userInfoMapper;
    }

    /**
     * Save a userInfo.
     *
     * @param userInfoDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public UserInfoDTO save(UserInfoDTO userInfoDTO) {
        log.debug("Request to save UserInfo : {}", userInfoDTO);
        UserInfo userInfo = userInfoMapper.toEntity(userInfoDTO);
        userInfo = userInfoRepository.save(userInfo);
        return userInfoMapper.toDto(userInfo);
    }

    /**
     * Get all the userInfos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    public Page<UserInfoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserInfos");
        return userInfoRepository.findAll(pageable)
            .map(userInfoMapper::toDto);
    }


    /**
     * Get one userInfo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<UserInfoDTO> findOne(String id) {
        log.debug("Request to get UserInfo : {}", id);
        return userInfoRepository.findById(id)
            .map(userInfoMapper::toDto);
    }

    /**
     * Delete the userInfo by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete UserInfo : {}", id);
        userInfoRepository.deleteById(id);
    }
}
