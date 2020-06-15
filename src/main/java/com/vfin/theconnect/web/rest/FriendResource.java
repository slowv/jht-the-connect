package com.vfin.theconnect.web.rest;

import com.vfin.theconnect.service.FriendService;
import com.vfin.theconnect.web.rest.errors.BadRequestAlertException;
import com.vfin.theconnect.service.dto.FriendDTO;

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
 * REST controller for managing {@link com.vfin.theconnect.domain.Friend}.
 */
@RestController
@RequestMapping("/api")
public class FriendResource {

    private final Logger log = LoggerFactory.getLogger(FriendResource.class);

    private static final String ENTITY_NAME = "friend";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FriendService friendService;

    public FriendResource(FriendService friendService) {
        this.friendService = friendService;
    }

    /**
     * {@code POST  /friends} : Create a new friend.
     *
     * @param friendDTO the friendDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new friendDTO, or with status {@code 400 (Bad Request)} if the friend has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/friends")
    public ResponseEntity<FriendDTO> createFriend(@Valid @RequestBody FriendDTO friendDTO) throws URISyntaxException {
        log.debug("REST request to save Friend : {}", friendDTO);
        if (friendDTO.getId() != null) {
            throw new BadRequestAlertException("A new friend cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FriendDTO result = friendService.save(friendDTO);
        return ResponseEntity.created(new URI("/api/friends/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /friends} : Updates an existing friend.
     *
     * @param friendDTO the friendDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated friendDTO,
     * or with status {@code 400 (Bad Request)} if the friendDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the friendDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/friends")
    public ResponseEntity<FriendDTO> updateFriend(@Valid @RequestBody FriendDTO friendDTO) throws URISyntaxException {
        log.debug("REST request to update Friend : {}", friendDTO);
        if (friendDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FriendDTO result = friendService.save(friendDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, friendDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /friends} : get all the friends.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of friends in body.
     */
    @GetMapping("/friends")
    public ResponseEntity<List<FriendDTO>> getAllFriends(Pageable pageable) {
        log.debug("REST request to get a page of Friends");
        Page<FriendDTO> page = friendService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /friends/:id} : get the "id" friend.
     *
     * @param id the id of the friendDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the friendDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/friends/{id}")
    public ResponseEntity<FriendDTO> getFriend(@PathVariable String id) {
        log.debug("REST request to get Friend : {}", id);
        Optional<FriendDTO> friendDTO = friendService.findOne(id);
        return ResponseUtil.wrapOrNotFound(friendDTO);
    }

    /**
     * {@code DELETE  /friends/:id} : delete the "id" friend.
     *
     * @param id the id of the friendDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/friends/{id}")
    public ResponseEntity<Void> deleteFriend(@PathVariable String id) {
        log.debug("REST request to delete Friend : {}", id);
        friendService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
