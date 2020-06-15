package com.vfin.theconnect.service;

import com.vfin.theconnect.service.dto.FriendDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.vfin.theconnect.domain.Friend}.
 */
public interface FriendService {

    /**
     * Save a friend.
     *
     * @param friendDTO the entity to save.
     * @return the persisted entity.
     */
    FriendDTO save(FriendDTO friendDTO);

    /**
     * Get all the friends.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FriendDTO> findAll(Pageable pageable);


    /**
     * Get the "id" friend.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FriendDTO> findOne(String id);

    /**
     * Delete the "id" friend.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
