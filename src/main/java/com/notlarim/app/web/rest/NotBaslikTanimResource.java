package com.notlarim.app.web.rest;

import com.notlarim.app.domain.NotBaslikTanim;
import com.notlarim.app.repository.NotBaslikTanimRepository;
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
 * REST controller for managing {@link com.notlarim.app.domain.NotBaslikTanim}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class NotBaslikTanimResource {

    private final Logger log = LoggerFactory.getLogger(NotBaslikTanimResource.class);

    private static final String ENTITY_NAME = "notBaslikTanim";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NotBaslikTanimRepository notBaslikTanimRepository;

    public NotBaslikTanimResource(NotBaslikTanimRepository notBaslikTanimRepository) {
        this.notBaslikTanimRepository = notBaslikTanimRepository;
    }

    /**
     * {@code POST  /not-baslik-tanims} : Create a new notBaslikTanim.
     *
     * @param notBaslikTanim the notBaslikTanim to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new notBaslikTanim, or with status {@code 400 (Bad Request)} if the notBaslikTanim has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/not-baslik-tanims")
    public ResponseEntity<NotBaslikTanim> createNotBaslikTanim(@Valid @RequestBody NotBaslikTanim notBaslikTanim)
        throws URISyntaxException {
        log.debug("REST request to save NotBaslikTanim : {}", notBaslikTanim);
        if (notBaslikTanim.getId() != null) {
            throw new BadRequestAlertException("A new notBaslikTanim cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NotBaslikTanim result = notBaslikTanimRepository.save(notBaslikTanim);
        return ResponseEntity
            .created(new URI("/api/not-baslik-tanims/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /not-baslik-tanims/:id} : Updates an existing notBaslikTanim.
     *
     * @param id the id of the notBaslikTanim to save.
     * @param notBaslikTanim the notBaslikTanim to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated notBaslikTanim,
     * or with status {@code 400 (Bad Request)} if the notBaslikTanim is not valid,
     * or with status {@code 500 (Internal Server Error)} if the notBaslikTanim couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/not-baslik-tanims/{id}")
    public ResponseEntity<NotBaslikTanim> updateNotBaslikTanim(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody NotBaslikTanim notBaslikTanim
    ) throws URISyntaxException {
        log.debug("REST request to update NotBaslikTanim : {}, {}", id, notBaslikTanim);
        if (notBaslikTanim.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, notBaslikTanim.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!notBaslikTanimRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        NotBaslikTanim result = notBaslikTanimRepository.save(notBaslikTanim);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, notBaslikTanim.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /not-baslik-tanims/:id} : Partial updates given fields of an existing notBaslikTanim, field will ignore if it is null
     *
     * @param id the id of the notBaslikTanim to save.
     * @param notBaslikTanim the notBaslikTanim to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated notBaslikTanim,
     * or with status {@code 400 (Bad Request)} if the notBaslikTanim is not valid,
     * or with status {@code 404 (Not Found)} if the notBaslikTanim is not found,
     * or with status {@code 500 (Internal Server Error)} if the notBaslikTanim couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/not-baslik-tanims/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<NotBaslikTanim> partialUpdateNotBaslikTanim(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody NotBaslikTanim notBaslikTanim
    ) throws URISyntaxException {
        log.debug("REST request to partial update NotBaslikTanim partially : {}, {}", id, notBaslikTanim);
        if (notBaslikTanim.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, notBaslikTanim.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!notBaslikTanimRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<NotBaslikTanim> result = notBaslikTanimRepository
            .findById(notBaslikTanim.getId())
            .map(existingNotBaslikTanim -> {
                if (notBaslikTanim.getBaslik() != null) {
                    existingNotBaslikTanim.setBaslik(notBaslikTanim.getBaslik());
                }

                return existingNotBaslikTanim;
            })
            .map(notBaslikTanimRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, notBaslikTanim.getId().toString())
        );
    }

    /**
     * {@code GET  /not-baslik-tanims} : get all the notBaslikTanims.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of notBaslikTanims in body.
     */
    @GetMapping("/not-baslik-tanims")
    public ResponseEntity<List<NotBaslikTanim>> getAllNotBaslikTanims(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of NotBaslikTanims");
        Page<NotBaslikTanim> page = notBaslikTanimRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /not-baslik-tanims/:id} : get the "id" notBaslikTanim.
     *
     * @param id the id of the notBaslikTanim to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the notBaslikTanim, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/not-baslik-tanims/{id}")
    public ResponseEntity<NotBaslikTanim> getNotBaslikTanim(@PathVariable Long id) {
        log.debug("REST request to get NotBaslikTanim : {}", id);
        Optional<NotBaslikTanim> notBaslikTanim = notBaslikTanimRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(notBaslikTanim);
    }

    /**
     * {@code DELETE  /not-baslik-tanims/:id} : delete the "id" notBaslikTanim.
     *
     * @param id the id of the notBaslikTanim to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/not-baslik-tanims/{id}")
    public ResponseEntity<Void> deleteNotBaslikTanim(@PathVariable Long id) {
        log.debug("REST request to delete NotBaslikTanim : {}", id);
        notBaslikTanimRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
