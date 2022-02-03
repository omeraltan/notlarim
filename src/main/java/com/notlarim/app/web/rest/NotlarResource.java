package com.notlarim.app.web.rest;

import com.notlarim.app.domain.Notlar;
import com.notlarim.app.repository.NotlarRepository;
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
 * REST controller for managing {@link com.notlarim.app.domain.Notlar}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class NotlarResource {

    private final Logger log = LoggerFactory.getLogger(NotlarResource.class);

    private static final String ENTITY_NAME = "notlar";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NotlarRepository notlarRepository;

    public NotlarResource(NotlarRepository notlarRepository) {
        this.notlarRepository = notlarRepository;
    }

    /**
     * {@code POST  /notlars} : Create a new notlar.
     *
     * @param notlar the notlar to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new notlar, or with status {@code 400 (Bad Request)} if the notlar has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/notlars")
    public ResponseEntity<Notlar> createNotlar(@Valid @RequestBody Notlar notlar) throws URISyntaxException {
        log.debug("REST request to save Notlar : {}", notlar);
        if (notlar.getId() != null) {
            throw new BadRequestAlertException("A new notlar cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Notlar result = notlarRepository.save(notlar);
        return ResponseEntity
            .created(new URI("/api/notlars/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /notlars/:id} : Updates an existing notlar.
     *
     * @param id the id of the notlar to save.
     * @param notlar the notlar to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated notlar,
     * or with status {@code 400 (Bad Request)} if the notlar is not valid,
     * or with status {@code 500 (Internal Server Error)} if the notlar couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/notlars/{id}")
    public ResponseEntity<Notlar> updateNotlar(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Notlar notlar
    ) throws URISyntaxException {
        log.debug("REST request to update Notlar : {}, {}", id, notlar);
        if (notlar.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, notlar.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!notlarRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Notlar result = notlarRepository.save(notlar);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, notlar.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /notlars/:id} : Partial updates given fields of an existing notlar, field will ignore if it is null
     *
     * @param id the id of the notlar to save.
     * @param notlar the notlar to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated notlar,
     * or with status {@code 400 (Bad Request)} if the notlar is not valid,
     * or with status {@code 404 (Not Found)} if the notlar is not found,
     * or with status {@code 500 (Internal Server Error)} if the notlar couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/notlars/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Notlar> partialUpdateNotlar(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Notlar notlar
    ) throws URISyntaxException {
        log.debug("REST request to partial update Notlar partially : {}, {}", id, notlar);
        if (notlar.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, notlar.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!notlarRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Notlar> result = notlarRepository
            .findById(notlar.getId())
            .map(existingNotlar -> {
                if (notlar.getNot() != null) {
                    existingNotlar.setNot(notlar.getNot());
                }

                return existingNotlar;
            })
            .map(notlarRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, notlar.getId().toString())
        );
    }

    /**
     * {@code GET  /notlars} : get all the notlars.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of notlars in body.
     */
    @GetMapping("/notlars")
    public ResponseEntity<List<Notlar>> getAllNotlars(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Notlars");
        Page<Notlar> page = notlarRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /notlars/:id} : get the "id" notlar.
     *
     * @param id the id of the notlar to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the notlar, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/notlars/{id}")
    public ResponseEntity<Notlar> getNotlar(@PathVariable Long id) {
        log.debug("REST request to get Notlar : {}", id);
        Optional<Notlar> notlar = notlarRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(notlar);
    }

    /**
     * {@code DELETE  /notlars/:id} : delete the "id" notlar.
     *
     * @param id the id of the notlar to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/notlars/{id}")
    public ResponseEntity<Void> deleteNotlar(@PathVariable Long id) {
        log.debug("REST request to delete Notlar : {}", id);
        notlarRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
