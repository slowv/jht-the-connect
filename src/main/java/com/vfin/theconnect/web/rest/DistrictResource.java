package com.vfin.theconnect.web.rest;

import com.vfin.theconnect.service.DistrictService;
import com.vfin.theconnect.web.rest.errors.BadRequestAlertException;
import com.vfin.theconnect.service.dto.DistrictDTO;

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
 * REST controller for managing {@link com.vfin.theconnect.domain.District}.
 */
@RestController
@RequestMapping("/api")
public class DistrictResource {

    private final Logger log = LoggerFactory.getLogger(DistrictResource.class);

    private static final String ENTITY_NAME = "district";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DistrictService districtService;

    public DistrictResource(DistrictService districtService) {
        this.districtService = districtService;
    }

    /**
     * {@code POST  /districts} : Create a new district.
     *
     * @param districtDTO the districtDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new districtDTO, or with status {@code 400 (Bad Request)} if the district has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/districts")
    public ResponseEntity<DistrictDTO> createDistrict(@Valid @RequestBody DistrictDTO districtDTO) throws URISyntaxException {
        log.debug("REST request to save District : {}", districtDTO);
        if (districtDTO.getId() != null) {
            throw new BadRequestAlertException("A new district cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DistrictDTO result = districtService.save(districtDTO);
        return ResponseEntity.created(new URI("/api/districts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /districts} : Updates an existing district.
     *
     * @param districtDTO the districtDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated districtDTO,
     * or with status {@code 400 (Bad Request)} if the districtDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the districtDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/districts")
    public ResponseEntity<DistrictDTO> updateDistrict(@Valid @RequestBody DistrictDTO districtDTO) throws URISyntaxException {
        log.debug("REST request to update District : {}", districtDTO);
        if (districtDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DistrictDTO result = districtService.save(districtDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, districtDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /districts} : get all the districts.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of districts in body.
     */
    @GetMapping("/districts")
    public ResponseEntity<List<DistrictDTO>> getAllDistricts(Pageable pageable) {
        log.debug("REST request to get a page of Districts");
        Page<DistrictDTO> page = districtService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /districts/:id} : get the "id" district.
     *
     * @param id the id of the districtDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the districtDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/districts/{id}")
    public ResponseEntity<DistrictDTO> getDistrict(@PathVariable String id) {
        log.debug("REST request to get District : {}", id);
        Optional<DistrictDTO> districtDTO = districtService.findOne(id);
        return ResponseUtil.wrapOrNotFound(districtDTO);
    }

    /**
     * {@code DELETE  /districts/:id} : delete the "id" district.
     *
     * @param id the id of the districtDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/districts/{id}")
    public ResponseEntity<Void> deleteDistrict(@PathVariable String id) {
        log.debug("REST request to delete District : {}", id);
        districtService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
