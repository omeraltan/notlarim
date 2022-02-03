package com.notlarim.app.web.rest;

import com.notlarim.app.domain.Rehber;
import com.notlarim.app.repository.RehberRepository;
import com.notlarim.app.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.notlarim.app.domain.Rehber}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class RehberResource {

    private final Logger log = LoggerFactory.getLogger(RehberResource.class);

    private static final String ENTITY_NAME = "rehber";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RehberRepository rehberRepository;

    public RehberResource(RehberRepository rehberRepository) {
        this.rehberRepository = rehberRepository;
    }

    /**
     * {@code POST  /rehbers} : Create a new rehber.
     *
     * @param rehber the rehber to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rehber, or with status {@code 400 (Bad Request)} if the rehber has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/rehbers")
    public ResponseEntity<Rehber> createRehber(@Valid @RequestBody Rehber rehber) throws URISyntaxException {
        log.debug("REST request to save Rehber : {}", rehber);
        if (rehber.getId() != null) {
            throw new BadRequestAlertException("A new rehber cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Rehber result = rehberRepository.save(rehber);
        return ResponseEntity
            .created(new URI("/api/rehbers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /rehbers/:id} : Updates an existing rehber.
     *
     * @param id the id of the rehber to save.
     * @param rehber the rehber to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rehber,
     * or with status {@code 400 (Bad Request)} if the rehber is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rehber couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/rehbers/{id}")
    public ResponseEntity<Rehber> updateRehber(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Rehber rehber
    ) throws URISyntaxException {
        log.debug("REST request to update Rehber : {}, {}", id, rehber);
        if (rehber.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rehber.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rehberRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Rehber result = rehberRepository.save(rehber);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rehber.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /rehbers/:id} : Partial updates given fields of an existing rehber, field will ignore if it is null
     *
     * @param id the id of the rehber to save.
     * @param rehber the rehber to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rehber,
     * or with status {@code 400 (Bad Request)} if the rehber is not valid,
     * or with status {@code 404 (Not Found)} if the rehber is not found,
     * or with status {@code 500 (Internal Server Error)} if the rehber couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/rehbers/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Rehber> partialUpdateRehber(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Rehber rehber
    ) throws URISyntaxException {
        log.debug("REST request to partial update Rehber partially : {}, {}", id, rehber);
        if (rehber.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rehber.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rehberRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Rehber> result = rehberRepository
            .findById(rehber.getId())
            .map(existingRehber -> {
                if (rehber.getTelefon() != null) {
                    existingRehber.setTelefon(rehber.getTelefon());
                }
                if (rehber.getAdres() != null) {
                    existingRehber.setAdres(rehber.getAdres());
                }

                return existingRehber;
            })
            .map(rehberRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rehber.getId().toString())
        );
    }

    /**
     * {@code GET  /rehbers} : get all the rehbers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rehbers in body.
     */
    @GetMapping("/rehbers")
    public ResponseEntity<List<Rehber>> getAllRehbers(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Rehbers");
        Page<Rehber> page = rehberRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /rehbers/:id} : get the "id" rehber.
     *
     * @param id the id of the rehber to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rehber, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rehbers/{id}")
    public ResponseEntity<Rehber> getRehber(@PathVariable Long id) {
        log.debug("REST request to get Rehber : {}", id);
        Optional<Rehber> rehber = rehberRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(rehber);
    }

    /**
     * {@code DELETE  /rehbers/:id} : delete the "id" rehber.
     *
     * @param id the id of the rehber to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/rehbers/{id}")
    public ResponseEntity<Void> deleteRehber(@PathVariable Long id) {
        log.debug("REST request to delete Rehber : {}", id);
        rehberRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
