package com.vfin.theconnect.service;

import com.vfin.theconnect.service.dto.AccountTCDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.vfin.theconnect.domain.AccountTC}.
 */
public interface AccountTCService {

    /**
     * Save a accountTC.
     *
     * @param accountTCDTO the entity to save.
     * @return the persisted entity.
     */
    AccountTCDTO save(AccountTCDTO accountTCDTO);

    /**
     * Get all the accountTCS.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AccountTCDTO> findAll(Pageable pageable);


    /**
     * Get the "id" accountTC.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AccountTCDTO> findOne(String id);

    /**
     * Delete the "id" accountTC.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
