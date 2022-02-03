package com.notlarim.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.notlarim.app.IntegrationTest;
import com.notlarim.app.domain.PersonelTanim;
import com.notlarim.app.domain.enumeration.Gorev;
import com.notlarim.app.repository.PersonelTanimRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PersonelTanimResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PersonelTanimResourceIT {

    private static final String DEFAULT_ADI = "AAAAAAAAAA";
    private static final String UPDATED_ADI = "BBBBBBBBBB";

    private static final String DEFAULT_SOYADI = "AAAAAAAAAA";
    private static final String UPDATED_SOYADI = "BBBBBBBBBB";

    private static final Gorev DEFAULT_GOREVTIP = Gorev.J_DEV_MEM;
    private static final Gorev UPDATED_GOREVTIP = Gorev.J_UZM;

    private static final String ENTITY_API_URL = "/api/personel-tanims";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PersonelTanimRepository personelTanimRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPersonelTanimMockMvc;

    private PersonelTanim personelTanim;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PersonelTanim createEntity(EntityManager em) {
        PersonelTanim personelTanim = new PersonelTanim().adi(DEFAULT_ADI).soyadi(DEFAULT_SOYADI).gorevtip(DEFAULT_GOREVTIP);
        return personelTanim;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PersonelTanim createUpdatedEntity(EntityManager em) {
        PersonelTanim personelTanim = new PersonelTanim().adi(UPDATED_ADI).soyadi(UPDATED_SOYADI).gorevtip(UPDATED_GOREVTIP);
        return personelTanim;
    }

    @BeforeEach
    public void initTest() {
        personelTanim = createEntity(em);
    }

    @Test
    @Transactional
    void createPersonelTanim() throws Exception {
        int databaseSizeBeforeCreate = personelTanimRepository.findAll().size();
        // Create the PersonelTanim
        restPersonelTanimMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(personelTanim)))
            .andExpect(status().isCreated());

        // Validate the PersonelTanim in the database
        List<PersonelTanim> personelTanimList = personelTanimRepository.findAll();
        assertThat(personelTanimList).hasSize(databaseSizeBeforeCreate + 1);
        PersonelTanim testPersonelTanim = personelTanimList.get(personelTanimList.size() - 1);
        assertThat(testPersonelTanim.getAdi()).isEqualTo(DEFAULT_ADI);
        assertThat(testPersonelTanim.getSoyadi()).isEqualTo(DEFAULT_SOYADI);
        assertThat(testPersonelTanim.getGorevtip()).isEqualTo(DEFAULT_GOREVTIP);
    }

    @Test
    @Transactional
    void createPersonelTanimWithExistingId() throws Exception {
        // Create the PersonelTanim with an existing ID
        personelTanim.setId(1L);

        int databaseSizeBeforeCreate = personelTanimRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonelTanimMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(personelTanim)))
            .andExpect(status().isBadRequest());

        // Validate the PersonelTanim in the database
        List<PersonelTanim> personelTanimList = personelTanimRepository.findAll();
        assertThat(personelTanimList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkAdiIsRequired() throws Exception {
        int databaseSizeBeforeTest = personelTanimRepository.findAll().size();
        // set the field null
        personelTanim.setAdi(null);

        // Create the PersonelTanim, which fails.

        restPersonelTanimMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(personelTanim)))
            .andExpect(status().isBadRequest());

        List<PersonelTanim> personelTanimList = personelTanimRepository.findAll();
        assertThat(personelTanimList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSoyadiIsRequired() throws Exception {
        int databaseSizeBeforeTest = personelTanimRepository.findAll().size();
        // set the field null
        personelTanim.setSoyadi(null);

        // Create the PersonelTanim, which fails.

        restPersonelTanimMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(personelTanim)))
            .andExpect(status().isBadRequest());

        List<PersonelTanim> personelTanimList = personelTanimRepository.findAll();
        assertThat(personelTanimList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkGorevtipIsRequired() throws Exception {
        int databaseSizeBeforeTest = personelTanimRepository.findAll().size();
        // set the field null
        personelTanim.setGorevtip(null);

        // Create the PersonelTanim, which fails.

        restPersonelTanimMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(personelTanim)))
            .andExpect(status().isBadRequest());

        List<PersonelTanim> personelTanimList = personelTanimRepository.findAll();
        assertThat(personelTanimList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPersonelTanims() throws Exception {
        // Initialize the database
        personelTanimRepository.saveAndFlush(personelTanim);

        // Get all the personelTanimList
        restPersonelTanimMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personelTanim.getId().intValue())))
            .andExpect(jsonPath("$.[*].adi").value(hasItem(DEFAULT_ADI)))
            .andExpect(jsonPath("$.[*].soyadi").value(hasItem(DEFAULT_SOYADI)))
            .andExpect(jsonPath("$.[*].gorevtip").value(hasItem(DEFAULT_GOREVTIP.toString())));
    }

    @Test
    @Transactional
    void getPersonelTanim() throws Exception {
        // Initialize the database
        personelTanimRepository.saveAndFlush(personelTanim);

        // Get the personelTanim
        restPersonelTanimMockMvc
            .perform(get(ENTITY_API_URL_ID, personelTanim.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(personelTanim.getId().intValue()))
            .andExpect(jsonPath("$.adi").value(DEFAULT_ADI))
            .andExpect(jsonPath("$.soyadi").value(DEFAULT_SOYADI))
            .andExpect(jsonPath("$.gorevtip").value(DEFAULT_GOREVTIP.toString()));
    }

    @Test
    @Transactional
    void getNonExistingPersonelTanim() throws Exception {
        // Get the personelTanim
        restPersonelTanimMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPersonelTanim() throws Exception {
        // Initialize the database
        personelTanimRepository.saveAndFlush(personelTanim);

        int databaseSizeBeforeUpdate = personelTanimRepository.findAll().size();

        // Update the personelTanim
        PersonelTanim updatedPersonelTanim = personelTanimRepository.findById(personelTanim.getId()).get();
        // Disconnect from session so that the updates on updatedPersonelTanim are not directly saved in db
        em.detach(updatedPersonelTanim);
        updatedPersonelTanim.adi(UPDATED_ADI).soyadi(UPDATED_SOYADI).gorevtip(UPDATED_GOREVTIP);

        restPersonelTanimMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPersonelTanim.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPersonelTanim))
            )
            .andExpect(status().isOk());

        // Validate the PersonelTanim in the database
        List<PersonelTanim> personelTanimList = personelTanimRepository.findAll();
        assertThat(personelTanimList).hasSize(databaseSizeBeforeUpdate);
        PersonelTanim testPersonelTanim = personelTanimList.get(personelTanimList.size() - 1);
        assertThat(testPersonelTanim.getAdi()).isEqualTo(UPDATED_ADI);
        assertThat(testPersonelTanim.getSoyadi()).isEqualTo(UPDATED_SOYADI);
        assertThat(testPersonelTanim.getGorevtip()).isEqualTo(UPDATED_GOREVTIP);
    }

    @Test
    @Transactional
    void putNonExistingPersonelTanim() throws Exception {
        int databaseSizeBeforeUpdate = personelTanimRepository.findAll().size();
        personelTanim.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersonelTanimMockMvc
            .perform(
                put(ENTITY_API_URL_ID, personelTanim.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(personelTanim))
            )
            .andExpect(status().isBadRequest());

        // Validate the PersonelTanim in the database
        List<PersonelTanim> personelTanimList = personelTanimRepository.findAll();
        assertThat(personelTanimList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPersonelTanim() throws Exception {
        int databaseSizeBeforeUpdate = personelTanimRepository.findAll().size();
        personelTanim.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonelTanimMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(personelTanim))
            )
            .andExpect(status().isBadRequest());

        // Validate the PersonelTanim in the database
        List<PersonelTanim> personelTanimList = personelTanimRepository.findAll();
        assertThat(personelTanimList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPersonelTanim() throws Exception {
        int databaseSizeBeforeUpdate = personelTanimRepository.findAll().size();
        personelTanim.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonelTanimMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(personelTanim)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PersonelTanim in the database
        List<PersonelTanim> personelTanimList = personelTanimRepository.findAll();
        assertThat(personelTanimList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePersonelTanimWithPatch() throws Exception {
        // Initialize the database
        personelTanimRepository.saveAndFlush(personelTanim);

        int databaseSizeBeforeUpdate = personelTanimRepository.findAll().size();

        // Update the personelTanim using partial update
        PersonelTanim partialUpdatedPersonelTanim = new PersonelTanim();
        partialUpdatedPersonelTanim.setId(personelTanim.getId());

        partialUpdatedPersonelTanim.adi(UPDATED_ADI).soyadi(UPDATED_SOYADI);

        restPersonelTanimMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPersonelTanim.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPersonelTanim))
            )
            .andExpect(status().isOk());

        // Validate the PersonelTanim in the database
        List<PersonelTanim> personelTanimList = personelTanimRepository.findAll();
        assertThat(personelTanimList).hasSize(databaseSizeBeforeUpdate);
        PersonelTanim testPersonelTanim = personelTanimList.get(personelTanimList.size() - 1);
        assertThat(testPersonelTanim.getAdi()).isEqualTo(UPDATED_ADI);
        assertThat(testPersonelTanim.getSoyadi()).isEqualTo(UPDATED_SOYADI);
        assertThat(testPersonelTanim.getGorevtip()).isEqualTo(DEFAULT_GOREVTIP);
    }

    @Test
    @Transactional
    void fullUpdatePersonelTanimWithPatch() throws Exception {
        // Initialize the database
        personelTanimRepository.saveAndFlush(personelTanim);

        int databaseSizeBeforeUpdate = personelTanimRepository.findAll().size();

        // Update the personelTanim using partial update
        PersonelTanim partialUpdatedPersonelTanim = new PersonelTanim();
        partialUpdatedPersonelTanim.setId(personelTanim.getId());

        partialUpdatedPersonelTanim.adi(UPDATED_ADI).soyadi(UPDATED_SOYADI).gorevtip(UPDATED_GOREVTIP);

        restPersonelTanimMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPersonelTanim.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPersonelTanim))
            )
            .andExpect(status().isOk());

        // Validate the PersonelTanim in the database
        List<PersonelTanim> personelTanimList = personelTanimRepository.findAll();
        assertThat(personelTanimList).hasSize(databaseSizeBeforeUpdate);
        PersonelTanim testPersonelTanim = personelTanimList.get(personelTanimList.size() - 1);
        assertThat(testPersonelTanim.getAdi()).isEqualTo(UPDATED_ADI);
        assertThat(testPersonelTanim.getSoyadi()).isEqualTo(UPDATED_SOYADI);
        assertThat(testPersonelTanim.getGorevtip()).isEqualTo(UPDATED_GOREVTIP);
    }

    @Test
    @Transactional
    void patchNonExistingPersonelTanim() throws Exception {
        int databaseSizeBeforeUpdate = personelTanimRepository.findAll().size();
        personelTanim.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersonelTanimMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, personelTanim.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(personelTanim))
            )
            .andExpect(status().isBadRequest());

        // Validate the PersonelTanim in the database
        List<PersonelTanim> personelTanimList = personelTanimRepository.findAll();
        assertThat(personelTanimList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPersonelTanim() throws Exception {
        int databaseSizeBeforeUpdate = personelTanimRepository.findAll().size();
        personelTanim.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonelTanimMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(personelTanim))
            )
            .andExpect(status().isBadRequest());

        // Validate the PersonelTanim in the database
        List<PersonelTanim> personelTanimList = personelTanimRepository.findAll();
        assertThat(personelTanimList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPersonelTanim() throws Exception {
        int databaseSizeBeforeUpdate = personelTanimRepository.findAll().size();
        personelTanim.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonelTanimMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(personelTanim))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PersonelTanim in the database
        List<PersonelTanim> personelTanimList = personelTanimRepository.findAll();
        assertThat(personelTanimList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePersonelTanim() throws Exception {
        // Initialize the database
        personelTanimRepository.saveAndFlush(personelTanim);

        int databaseSizeBeforeDelete = personelTanimRepository.findAll().size();

        // Delete the personelTanim
        restPersonelTanimMockMvc
            .perform(delete(ENTITY_API_URL_ID, personelTanim.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PersonelTanim> personelTanimList = personelTanimRepository.findAll();
        assertThat(personelTanimList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
