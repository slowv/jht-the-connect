package com.vfin.theconnect.service.impl;

import com.vfin.theconnect.service.AccountTCService;
import com.vfin.theconnect.domain.AccountTC;
import com.vfin.theconnect.repository.AccountTCRepository;
import com.vfin.theconnect.service.dto.AccountTCDTO;
import com.vfin.theconnect.service.mapper.AccountTCMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service Implementation for managing {@link AccountTC}.
 */
@Service
public class AccountTCServiceImpl implements AccountTCService {

    private final Logger log = LoggerFactory.getLogger(AccountTCServiceImpl.class);

    private final AccountTCRepository accountTCRepository;

    private final AccountTCMapper accountTCMapper;

    public AccountTCServiceImpl(AccountTCRepository accountTCRepository, AccountTCMapper accountTCMapper) {
        this.accountTCRepository = accountTCRepository;
        this.accountTCMapper = accountTCMapper;
    }

    /**
     * Save a accountTC.
     *
     * @param accountTCDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public AccountTCDTO save(AccountTCDTO accountTCDTO) {
        log.debug("Request to save AccountTC : {}", accountTCDTO);
        AccountTC accountTC = accountTCMapper.toEntity(accountTCDTO);
        accountTC = accountTCRepository.save(accountTC);
        return accountTCMapper.toDto(accountTC);
    }

    /**
     * Get all the accountTCS.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    public Page<AccountTCDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AccountTCS");
        return accountTCRepository.findAll(pageable)
            .map(accountTCMapper::toDto);
    }


    /**
     * Get one accountTC by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<AccountTCDTO> findOne(String id) {
        log.debug("Request to get AccountTC : {}", id);
        return accountTCRepository.findById(id)
            .map(accountTCMapper::toDto);
    }

    /**
     * Delete the accountTC by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete AccountTC : {}", id);
        accountTCRepository.deleteById(id);
    }
}
