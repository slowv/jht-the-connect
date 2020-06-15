package com.vfin.theconnect.web.rest;

import com.vfin.theconnect.service.AccountTCService;
import com.vfin.theconnect.web.rest.errors.BadRequestAlertException;
import com.vfin.theconnect.service.dto.AccountTCDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.vfin.theconnect.domain.AccountTC}.
 */
@RestController
@RequestMapping("/api")
public class AccountTCResource {

    private final Logger log = LoggerFactory.getLogger(AccountTCResource.class);

    private static final String ENTITY_NAME = "accountTC";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AccountTCService accountTCService;

    public AccountTCResource(AccountTCService accountTCService) {
        this.accountTCService = accountTCService;
    }

    /**
     * {@code POST  /account-tcs} : Create a new accountTC.
     *
     * @param accountTCDTO the accountTCDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new accountTCDTO, or with status {@code 400 (Bad Request)} if the accountTC has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/account-tcs")
    public ResponseEntity<AccountTCDTO> createAccountTC(@Valid @RequestBody AccountTCDTO accountTCDTO) throws URISyntaxException {
        log.debug("REST request to save AccountTC : {}", accountTCDTO);
        if (accountTCDTO.getId() != null) {
            throw new BadRequestAlertException("A new accountTC cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AccountTCDTO result = accountTCService.save(accountTCDTO);
        return ResponseEntity.created(new URI("/api/account-tcs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /account-tcs} : Updates an existing accountTC.
     *
     * @param accountTCDTO the accountTCDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated accountTCDTO,
     * or with status {@code 400 (Bad Request)} if the accountTCDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the accountTCDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/account-tcs")
    public ResponseEntity<AccountTCDTO> updateAccountTC(@Valid @RequestBody AccountTCDTO accountTCDTO) throws URISyntaxException {
        log.debug("REST request to update AccountTC : {}", accountTCDTO);
        if (accountTCDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AccountTCDTO result = accountTCService.save(accountTCDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, accountTCDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /account-tcs} : get all the accountTCS.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of accountTCS in body.
     */
    @GetMapping("/account-tcs")
    public ResponseEntity<List<AccountTCDTO>> getAllAccountTCS(Pageable pageable) {
        log.debug("REST request to get a page of AccountTCS");
        Page<AccountTCDTO> page = accountTCService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /account-tcs/:id} : get the "id" accountTC.
     *
     * @param id the id of the accountTCDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the accountTCDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/account-tcs/{id}")
    public ResponseEntity<AccountTCDTO> getAccountTC(@PathVariable String id) {
        log.debug("REST request to get AccountTC : {}", id);
        Optional<AccountTCDTO> accountTCDTO = accountTCService.findOne(id);
        return ResponseUtil.wrapOrNotFound(accountTCDTO);
    }

    /**
     * {@code DELETE  /account-tcs/:id} : delete the "id" accountTC.
     *
     * @param id the id of the accountTCDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/account-tcs/{id}")
    public ResponseEntity<Void> deleteAccountTC(@PathVariable String id) {
        log.debug("REST request to delete AccountTC : {}", id);
        accountTCService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
