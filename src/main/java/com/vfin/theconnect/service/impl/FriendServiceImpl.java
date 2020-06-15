package com.vfin.theconnect.service.impl;

import com.vfin.theconnect.service.FriendService;
import com.vfin.theconnect.domain.Friend;
import com.vfin.theconnect.repository.FriendRepository;
import com.vfin.theconnect.service.dto.FriendDTO;
import com.vfin.theconnect.service.mapper.FriendMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Friend}.
 */
@Service
public class FriendServiceImpl implements FriendService {

    private final Logger log = LoggerFactory.getLogger(FriendServiceImpl.class);

    private final FriendRepository friendRepository;

    private final FriendMapper friendMapper;

    public FriendServiceImpl(FriendRepository friendRepository, FriendMapper friendMapper) {
        this.friendRepository = friendRepository;
        this.friendMapper = friendMapper;
    }

    /**
     * Save a friend.
     *
     * @param friendDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public FriendDTO save(FriendDTO friendDTO) {
        log.debug("Request to save Friend : {}", friendDTO);
        Friend friend = friendMapper.toEntity(friendDTO);
        friend = friendRepository.save(friend);
        return friendMapper.toDto(friend);
    }

    /**
     * Get all the friends.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    public Page<FriendDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Friends");
        return friendRepository.findAll(pageable)
            .map(friendMapper::toDto);
    }


    /**
     * Get one friend by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<FriendDTO> findOne(String id) {
        log.debug("Request to get Friend : {}", id);
        return friendRepository.findById(id)
            .map(friendMapper::toDto);
    }

    /**
     * Delete the friend by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Friend : {}", id);
        friendRepository.deleteById(id);
    }
}
