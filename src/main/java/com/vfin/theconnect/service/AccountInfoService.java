package com.vfin.theconnect.service;

import com.vfin.theconnect.service.dto.AccountInfoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.vfin.theconnect.domain.AccountInfo}.
 */
public interface AccountInfoService {

    /**
     * Save a accountInfo.
     *
     * @param accountInfoDTO the entity to save.
     * @return the persisted entity.
     */
    AccountInfoDTO save(AccountInfoDTO accountInfoDTO);

    /**
     * Get all the accountInfos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AccountInfoDTO> findAll(Pageable pageable);
    /**
     * Get all the AccountInfoDTO where AccountTC is {@code null}.
     *
     * @return the list of entities.
     */
    List<AccountInfoDTO> findAllWhereAccountTCIsNull();


    /**
     * Get the "id" accountInfo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AccountInfoDTO> findOne(String id);

    /**
     * Delete the "id" accountInfo.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
