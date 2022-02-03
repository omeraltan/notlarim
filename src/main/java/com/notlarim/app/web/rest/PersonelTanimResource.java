package com.notlarim.app.web.rest;

import com.notlarim.app.domain.PersonelTanim;
import com.notlarim.app.repository.PersonelTanimRepository;
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
 * REST controller for managing {@link com.notlarim.app.domain.PersonelTanim}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PersonelTanimResource {

    private final Logger log = LoggerFactory.getLogger(PersonelTanimResource.class);

    private static final String ENTITY_NAME = "personelTanim";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PersonelTanimRepository personelTanimRepository;

    public PersonelTanimResource(PersonelTanimRepository personelTanimRepository) {
        this.personelTanimRepository = personelTanimRepository;
    }

    /**
     * {@code POST  /personel-tanims} : Create a new personelTanim.
     *
     * @param personelTanim the personelTanim to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new personelTanim, or with status {@code 400 (Bad Request)} if the personelTanim has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/personel-tanims")
    public ResponseEntity<PersonelTanim> createPersonelTanim(@Valid @RequestBody PersonelTanim personelTanim) throws URISyntaxException {
        log.debug("REST request to save PersonelTanim : {}", personelTanim);
        if (personelTanim.getId() != null) {
            throw new BadRequestAlertException("A new personelTanim cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PersonelTanim result = personelTanimRepository.save(personelTanim);
        return ResponseEntity
            .created(new URI("/api/personel-tanims/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /personel-tanims/:id} : Updates an existing personelTanim.
     *
     * @param id the id of the personelTanim to save.
     * @param personelTanim the personelTanim to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated personelTanim,
     * or with status {@code 400 (Bad Request)} if the personelTanim is not valid,
     * or with status {@code 500 (Internal Server Error)} if the personelTanim couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/personel-tanims/{id}")
    public ResponseEntity<PersonelTanim> updatePersonelTanim(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PersonelTanim personelTanim
    ) throws URISyntaxException {
        log.debug("REST request to update PersonelTanim : {}, {}", id, personelTanim);
        if (personelTanim.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, personelTanim.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!personelTanimRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PersonelTanim result = personelTanimRepository.save(personelTanim);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, personelTanim.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /personel-tanims/:id} : Partial updates given fields of an existing personelTanim, field will ignore if it is null
     *
     * @param id the id of the personelTanim to save.
     * @param personelTanim the personelTanim to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated personelTanim,
     * or with status {@code 400 (Bad Request)} if the personelTanim is not valid,
     * or with status {@code 404 (Not Found)} if the personelTanim is not found,
     * or with status {@code 500 (Internal Server Error)} if the personelTanim couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/personel-tanims/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PersonelTanim> partialUpdatePersonelTanim(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PersonelTanim personelTanim
    ) throws URISyntaxException {
        log.debug("REST request to partial update PersonelTanim partially : {}, {}", id, personelTanim);
        if (personelTanim.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, personelTanim.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!personelTanimRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PersonelTanim> result = personelTanimRepository
            .findById(personelTanim.getId())
            .map(existingPersonelTanim -> {
                if (personelTanim.getAdi() != null) {
                    existingPersonelTanim.setAdi(personelTanim.getAdi());
                }
                if (personelTanim.getSoyadi() != null) {
                    existingPersonelTanim.setSoyadi(personelTanim.getSoyadi());
                }
                if (personelTanim.getGorevtip() != null) {
                    existingPersonelTanim.setGorevtip(personelTanim.getGorevtip());
                }

                return existingPersonelTanim;
            })
            .map(personelTanimRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, personelTanim.getId().toString())
        );
    }

    /**
     * {@code GET  /personel-tanims} : get all the personelTanims.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of personelTanims in body.
     */
    @GetMapping("/personel-tanims")
    public ResponseEntity<List<PersonelTanim>> getAllPersonelTanims(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of PersonelTanims");
        Page<PersonelTanim> page = personelTanimRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /personel-tanims/:id} : get the "id" personelTanim.
     *
     * @param id the id of the personelTanim to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the personelTanim, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/personel-tanims/{id}")
    public ResponseEntity<PersonelTanim> getPersonelTanim(@PathVariable Long id) {
        log.debug("REST request to get PersonelTanim : {}", id);
        Optional<PersonelTanim> personelTanim = personelTanimRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(personelTanim);
    }

    /**
     * {@code DELETE  /personel-tanims/:id} : delete the "id" personelTanim.
     *
     * @param id the id of the personelTanim to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/personel-tanims/{id}")
    public ResponseEntity<Void> deletePersonelTanim(@PathVariable Long id) {
        log.debug("REST request to delete PersonelTanim : {}", id);
        personelTanimRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
