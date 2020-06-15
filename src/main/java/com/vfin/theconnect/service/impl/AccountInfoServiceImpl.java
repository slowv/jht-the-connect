package com.vfin.theconnect.service.impl;

import com.vfin.theconnect.service.AccountInfoService;
import com.vfin.theconnect.domain.AccountInfo;
import com.vfin.theconnect.repository.AccountInfoRepository;
import com.vfin.theconnect.service.dto.AccountInfoDTO;
import com.vfin.theconnect.service.mapper.AccountInfoMapper;
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
 * Service Implementation for managing {@link AccountInfo}.
 */
@Service
public class AccountInfoServiceImpl implements AccountInfoService {

    private final Logger log = LoggerFactory.getLogger(AccountInfoServiceImpl.class);

    private final AccountInfoRepository accountInfoRepository;

    private final AccountInfoMapper accountInfoMapper;

    public AccountInfoServiceImpl(AccountInfoRepository accountInfoRepository, AccountInfoMapper accountInfoMapper) {
        this.accountInfoRepository = accountInfoRepository;
        this.accountInfoMapper = accountInfoMapper;
    }

    /**
     * Save a accountInfo.
     *
     * @param accountInfoDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public AccountInfoDTO save(AccountInfoDTO accountInfoDTO) {
        log.debug("Request to save AccountInfo : {}", accountInfoDTO);
        AccountInfo accountInfo = accountInfoMapper.toEntity(accountInfoDTO);
        accountInfo = accountInfoRepository.save(accountInfo);
        return accountInfoMapper.toDto(accountInfo);
    }

    /**
     * Get all the accountInfos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    public Page<AccountInfoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AccountInfos");
        return accountInfoRepository.findAll(pageable)
            .map(accountInfoMapper::toDto);
    }



    /**
    *  Get all the accountInfos where AccountTC is {@code null}.
     *  @return the list of entities.
     */
    public List<AccountInfoDTO> findAllWhereAccountTCIsNull() {
        log.debug("Request to get all accountInfos where AccountTC is null");
        return StreamSupport
            .stream(accountInfoRepository.findAll().spliterator(), false)
            .filter(accountInfo -> accountInfo.getAccountTC() == null)
            .map(accountInfoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one accountInfo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<AccountInfoDTO> findOne(String id) {
        log.debug("Request to get AccountInfo : {}", id);
        return accountInfoRepository.findById(id)
            .map(accountInfoMapper::toDto);
    }

    /**
     * Delete the accountInfo by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete AccountInfo : {}", id);
        accountInfoRepository.deleteById(id);
    }
}
