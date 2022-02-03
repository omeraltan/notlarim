package com.notlarim.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.notlarim.app.IntegrationTest;
import com.notlarim.app.domain.NotBaslikTanim;
import com.notlarim.app.repository.NotBaslikTanimRepository;
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
 * Integration tests for the {@link NotBaslikTanimResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NotBaslikTanimResourceIT {

    private static final String DEFAULT_BASLIK = "AAAAAAAAAA";
    private static final String UPDATED_BASLIK = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/not-baslik-tanims";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private NotBaslikTanimRepository notBaslikTanimRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNotBaslikTanimMockMvc;

    private NotBaslikTanim notBaslikTanim;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NotBaslikTanim createEntity(EntityManager em) {
        NotBaslikTanim notBaslikTanim = new NotBaslikTanim().baslik(DEFAULT_BASLIK);
        return notBaslikTanim;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NotBaslikTanim createUpdatedEntity(EntityManager em) {
        NotBaslikTanim notBaslikTanim = new NotBaslikTanim().baslik(UPDATED_BASLIK);
        return notBaslikTanim;
    }

    @BeforeEach
    public void initTest() {
        notBaslikTanim = createEntity(em);
    }

    @Test
    @Transactional
    void createNotBaslikTanim() throws Exception {
        int databaseSizeBeforeCreate = notBaslikTanimRepository.findAll().size();
        // Create the NotBaslikTanim
        restNotBaslikTanimMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(notBaslikTanim))
            )
            .andExpect(status().isCreated());

        // Validate the NotBaslikTanim in the database
        List<NotBaslikTanim> notBaslikTanimList = notBaslikTanimRepository.findAll();
        assertThat(notBaslikTanimList).hasSize(databaseSizeBeforeCreate + 1);
        NotBaslikTanim testNotBaslikTanim = notBaslikTanimList.get(notBaslikTanimList.size() - 1);
        assertThat(testNotBaslikTanim.getBaslik()).isEqualTo(DEFAULT_BASLIK);
    }

    @Test
    @Transactional
    void createNotBaslikTanimWithExistingId() throws Exception {
        // Create the NotBaslikTanim with an existing ID
        notBaslikTanim.setId(1L);

        int databaseSizeBeforeCreate = notBaslikTanimRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNotBaslikTanimMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(notBaslikTanim))
            )
            .andExpect(status().isBadRequest());

        // Validate the NotBaslikTanim in the database
        List<NotBaslikTanim> notBaslikTanimList = notBaslikTanimRepository.findAll();
        assertThat(notBaslikTanimList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkBaslikIsRequired() throws Exception {
        int databaseSizeBeforeTest = notBaslikTanimRepository.findAll().size();
        // set the field null
        notBaslikTanim.setBaslik(null);

        // Create the NotBaslikTanim, which fails.

        restNotBaslikTanimMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(notBaslikTanim))
            )
            .andExpect(status().isBadRequest());

        List<NotBaslikTanim> notBaslikTanimList = notBaslikTanimRepository.findAll();
        assertThat(notBaslikTanimList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllNotBaslikTanims() throws Exception {
        // Initialize the database
        notBaslikTanimRepository.saveAndFlush(notBaslikTanim);

        // Get all the notBaslikTanimList
        restNotBaslikTanimMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(notBaslikTanim.getId().intValue())))
            .andExpect(jsonPath("$.[*].baslik").value(hasItem(DEFAULT_BASLIK)));
    }

    @Test
    @Transactional
    void getNotBaslikTanim() throws Exception {
        // Initialize the database
        notBaslikTanimRepository.saveAndFlush(notBaslikTanim);

        // Get the notBaslikTanim
        restNotBaslikTanimMockMvc
            .perform(get(ENTITY_API_URL_ID, notBaslikTanim.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(notBaslikTanim.getId().intValue()))
            .andExpect(jsonPath("$.baslik").value(DEFAULT_BASLIK));
    }

    @Test
    @Transactional
    void getNonExistingNotBaslikTanim() throws Exception {
        // Get the notBaslikTanim
        restNotBaslikTanimMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewNotBaslikTanim() throws Exception {
        // Initialize the database
        notBaslikTanimRepository.saveAndFlush(notBaslikTanim);

        int databaseSizeBeforeUpdate = notBaslikTanimRepository.findAll().size();

        // Update the notBaslikTanim
        NotBaslikTanim updatedNotBaslikTanim = notBaslikTanimRepository.findById(notBaslikTanim.getId()).get();
        // Disconnect from session so that the updates on updatedNotBaslikTanim are not directly saved in db
        em.detach(updatedNotBaslikTanim);
        updatedNotBaslikTanim.baslik(UPDATED_BASLIK);

        restNotBaslikTanimMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedNotBaslikTanim.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedNotBaslikTanim))
            )
            .andExpect(status().isOk());

        // Validate the NotBaslikTanim in the database
        List<NotBaslikTanim> notBaslikTanimList = notBaslikTanimRepository.findAll();
        assertThat(notBaslikTanimList).hasSize(databaseSizeBeforeUpdate);
        NotBaslikTanim testNotBaslikTanim = notBaslikTanimList.get(notBaslikTanimList.size() - 1);
        assertThat(testNotBaslikTanim.getBaslik()).isEqualTo(UPDATED_BASLIK);
    }

    @Test
    @Transactional
    void putNonExistingNotBaslikTanim() throws Exception {
        int databaseSizeBeforeUpdate = notBaslikTanimRepository.findAll().size();
        notBaslikTanim.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNotBaslikTanimMockMvc
            .perform(
                put(ENTITY_API_URL_ID, notBaslikTanim.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(notBaslikTanim))
            )
            .andExpect(status().isBadRequest());

        // Validate the NotBaslikTanim in the database
        List<NotBaslikTanim> notBaslikTanimList = notBaslikTanimRepository.findAll();
        assertThat(notBaslikTanimList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNotBaslikTanim() throws Exception {
        int databaseSizeBeforeUpdate = notBaslikTanimRepository.findAll().size();
        notBaslikTanim.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNotBaslikTanimMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(notBaslikTanim))
            )
            .andExpect(status().isBadRequest());

        // Validate the NotBaslikTanim in the database
        List<NotBaslikTanim> notBaslikTanimList = notBaslikTanimRepository.findAll();
        assertThat(notBaslikTanimList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNotBaslikTanim() throws Exception {
        int databaseSizeBeforeUpdate = notBaslikTanimRepository.findAll().size();
        notBaslikTanim.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNotBaslikTanimMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(notBaslikTanim)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the NotBaslikTanim in the database
        List<NotBaslikTanim> notBaslikTanimList = notBaslikTanimRepository.findAll();
        assertThat(notBaslikTanimList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNotBaslikTanimWithPatch() throws Exception {
        // Initialize the database
        notBaslikTanimRepository.saveAndFlush(notBaslikTanim);

        int databaseSizeBeforeUpdate = notBaslikTanimRepository.findAll().size();

        // Update the notBaslikTanim using partial update
        NotBaslikTanim partialUpdatedNotBaslikTanim = new NotBaslikTanim();
        partialUpdatedNotBaslikTanim.setId(notBaslikTanim.getId());

        restNotBaslikTanimMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNotBaslikTanim.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNotBaslikTanim))
            )
            .andExpect(status().isOk());

        // Validate the NotBaslikTanim in the database
        List<NotBaslikTanim> notBaslikTanimList = notBaslikTanimRepository.findAll();
        assertThat(notBaslikTanimList).hasSize(databaseSizeBeforeUpdate);
        NotBaslikTanim testNotBaslikTanim = notBaslikTanimList.get(notBaslikTanimList.size() - 1);
        assertThat(testNotBaslikTanim.getBaslik()).isEqualTo(DEFAULT_BASLIK);
    }

    @Test
    @Transactional
    void fullUpdateNotBaslikTanimWithPatch() throws Exception {
        // Initialize the database
        notBaslikTanimRepository.saveAndFlush(notBaslikTanim);

        int databaseSizeBeforeUpdate = notBaslikTanimRepository.findAll().size();

        // Update the notBaslikTanim using partial update
        NotBaslikTanim partialUpdatedNotBaslikTanim = new NotBaslikTanim();
        partialUpdatedNotBaslikTanim.setId(notBaslikTanim.getId());

        partialUpdatedNotBaslikTanim.baslik(UPDATED_BASLIK);

        restNotBaslikTanimMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNotBaslikTanim.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNotBaslikTanim))
            )
            .andExpect(status().isOk());

        // Validate the NotBaslikTanim in the database
        List<NotBaslikTanim> notBaslikTanimList = notBaslikTanimRepository.findAll();
        assertThat(notBaslikTanimList).hasSize(databaseSizeBeforeUpdate);
        NotBaslikTanim testNotBaslikTanim = notBaslikTanimList.get(notBaslikTanimList.size() - 1);
        assertThat(testNotBaslikTanim.getBaslik()).isEqualTo(UPDATED_BASLIK);
    }

    @Test
    @Transactional
    void patchNonExistingNotBaslikTanim() throws Exception {
        int databaseSizeBeforeUpdate = notBaslikTanimRepository.findAll().size();
        notBaslikTanim.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNotBaslikTanimMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, notBaslikTanim.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(notBaslikTanim))
            )
            .andExpect(status().isBadRequest());

        // Validate the NotBaslikTanim in the database
        List<NotBaslikTanim> notBaslikTanimList = notBaslikTanimRepository.findAll();
        assertThat(notBaslikTanimList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNotBaslikTanim() throws Exception {
        int databaseSizeBeforeUpdate = notBaslikTanimRepository.findAll().size();
        notBaslikTanim.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNotBaslikTanimMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(notBaslikTanim))
            )
            .andExpect(status().isBadRequest());

        // Validate the NotBaslikTanim in the database
        List<NotBaslikTanim> notBaslikTanimList = notBaslikTanimRepository.findAll();
        assertThat(notBaslikTanimList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNotBaslikTanim() throws Exception {
        int databaseSizeBeforeUpdate = notBaslikTanimRepository.findAll().size();
        notBaslikTanim.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNotBaslikTanimMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(notBaslikTanim))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NotBaslikTanim in the database
        List<NotBaslikTanim> notBaslikTanimList = notBaslikTanimRepository.findAll();
        assertThat(notBaslikTanimList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNotBaslikTanim() throws Exception {
        // Initialize the database
        notBaslikTanimRepository.saveAndFlush(notBaslikTanim);

        int databaseSizeBeforeDelete = notBaslikTanimRepository.findAll().size();

        // Delete the notBaslikTanim
        restNotBaslikTanimMockMvc
            .perform(delete(ENTITY_API_URL_ID, notBaslikTanim.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NotBaslikTanim> notBaslikTanimList = notBaslikTanimRepository.findAll();
        assertThat(notBaslikTanimList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
