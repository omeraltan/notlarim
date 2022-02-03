package com.notlarim.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.notlarim.app.IntegrationTest;
import com.notlarim.app.domain.Notlar;
import com.notlarim.app.repository.NotlarRepository;
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
 * Integration tests for the {@link NotlarResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NotlarResourceIT {

    private static final String DEFAULT_NOT = "AAAAAAAAAA";
    private static final String UPDATED_NOT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/notlars";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private NotlarRepository notlarRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNotlarMockMvc;

    private Notlar notlar;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Notlar createEntity(EntityManager em) {
        Notlar notlar = new Notlar().not(DEFAULT_NOT);
        return notlar;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Notlar createUpdatedEntity(EntityManager em) {
        Notlar notlar = new Notlar().not(UPDATED_NOT);
        return notlar;
    }

    @BeforeEach
    public void initTest() {
        notlar = createEntity(em);
    }

    @Test
    @Transactional
    void createNotlar() throws Exception {
        int databaseSizeBeforeCreate = notlarRepository.findAll().size();
        // Create the Notlar
        restNotlarMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(notlar)))
            .andExpect(status().isCreated());

        // Validate the Notlar in the database
        List<Notlar> notlarList = notlarRepository.findAll();
        assertThat(notlarList).hasSize(databaseSizeBeforeCreate + 1);
        Notlar testNotlar = notlarList.get(notlarList.size() - 1);
        assertThat(testNotlar.getNot()).isEqualTo(DEFAULT_NOT);
    }

    @Test
    @Transactional
    void createNotlarWithExistingId() throws Exception {
        // Create the Notlar with an existing ID
        notlar.setId(1L);

        int databaseSizeBeforeCreate = notlarRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNotlarMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(notlar)))
            .andExpect(status().isBadRequest());

        // Validate the Notlar in the database
        List<Notlar> notlarList = notlarRepository.findAll();
        assertThat(notlarList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNotIsRequired() throws Exception {
        int databaseSizeBeforeTest = notlarRepository.findAll().size();
        // set the field null
        notlar.setNot(null);

        // Create the Notlar, which fails.

        restNotlarMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(notlar)))
            .andExpect(status().isBadRequest());

        List<Notlar> notlarList = notlarRepository.findAll();
        assertThat(notlarList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllNotlars() throws Exception {
        // Initialize the database
        notlarRepository.saveAndFlush(notlar);

        // Get all the notlarList
        restNotlarMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(notlar.getId().intValue())))
            .andExpect(jsonPath("$.[*].not").value(hasItem(DEFAULT_NOT)));
    }

    @Test
    @Transactional
    void getNotlar() throws Exception {
        // Initialize the database
        notlarRepository.saveAndFlush(notlar);

        // Get the notlar
        restNotlarMockMvc
            .perform(get(ENTITY_API_URL_ID, notlar.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(notlar.getId().intValue()))
            .andExpect(jsonPath("$.not").value(DEFAULT_NOT));
    }

    @Test
    @Transactional
    void getNonExistingNotlar() throws Exception {
        // Get the notlar
        restNotlarMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewNotlar() throws Exception {
        // Initialize the database
        notlarRepository.saveAndFlush(notlar);

        int databaseSizeBeforeUpdate = notlarRepository.findAll().size();

        // Update the notlar
        Notlar updatedNotlar = notlarRepository.findById(notlar.getId()).get();
        // Disconnect from session so that the updates on updatedNotlar are not directly saved in db
        em.detach(updatedNotlar);
        updatedNotlar.not(UPDATED_NOT);

        restNotlarMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedNotlar.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedNotlar))
            )
            .andExpect(status().isOk());

        // Validate the Notlar in the database
        List<Notlar> notlarList = notlarRepository.findAll();
        assertThat(notlarList).hasSize(databaseSizeBeforeUpdate);
        Notlar testNotlar = notlarList.get(notlarList.size() - 1);
        assertThat(testNotlar.getNot()).isEqualTo(UPDATED_NOT);
    }

    @Test
    @Transactional
    void putNonExistingNotlar() throws Exception {
        int databaseSizeBeforeUpdate = notlarRepository.findAll().size();
        notlar.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNotlarMockMvc
            .perform(
                put(ENTITY_API_URL_ID, notlar.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(notlar))
            )
            .andExpect(status().isBadRequest());

        // Validate the Notlar in the database
        List<Notlar> notlarList = notlarRepository.findAll();
        assertThat(notlarList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNotlar() throws Exception {
        int databaseSizeBeforeUpdate = notlarRepository.findAll().size();
        notlar.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNotlarMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(notlar))
            )
            .andExpect(status().isBadRequest());

        // Validate the Notlar in the database
        List<Notlar> notlarList = notlarRepository.findAll();
        assertThat(notlarList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNotlar() throws Exception {
        int databaseSizeBeforeUpdate = notlarRepository.findAll().size();
        notlar.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNotlarMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(notlar)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Notlar in the database
        List<Notlar> notlarList = notlarRepository.findAll();
        assertThat(notlarList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNotlarWithPatch() throws Exception {
        // Initialize the database
        notlarRepository.saveAndFlush(notlar);

        int databaseSizeBeforeUpdate = notlarRepository.findAll().size();

        // Update the notlar using partial update
        Notlar partialUpdatedNotlar = new Notlar();
        partialUpdatedNotlar.setId(notlar.getId());

        partialUpdatedNotlar.not(UPDATED_NOT);

        restNotlarMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNotlar.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNotlar))
            )
            .andExpect(status().isOk());

        // Validate the Notlar in the database
        List<Notlar> notlarList = notlarRepository.findAll();
        assertThat(notlarList).hasSize(databaseSizeBeforeUpdate);
        Notlar testNotlar = notlarList.get(notlarList.size() - 1);
        assertThat(testNotlar.getNot()).isEqualTo(UPDATED_NOT);
    }

    @Test
    @Transactional
    void fullUpdateNotlarWithPatch() throws Exception {
        // Initialize the database
        notlarRepository.saveAndFlush(notlar);

        int databaseSizeBeforeUpdate = notlarRepository.findAll().size();

        // Update the notlar using partial update
        Notlar partialUpdatedNotlar = new Notlar();
        partialUpdatedNotlar.setId(notlar.getId());

        partialUpdatedNotlar.not(UPDATED_NOT);

        restNotlarMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNotlar.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNotlar))
            )
            .andExpect(status().isOk());

        // Validate the Notlar in the database
        List<Notlar> notlarList = notlarRepository.findAll();
        assertThat(notlarList).hasSize(databaseSizeBeforeUpdate);
        Notlar testNotlar = notlarList.get(notlarList.size() - 1);
        assertThat(testNotlar.getNot()).isEqualTo(UPDATED_NOT);
    }

    @Test
    @Transactional
    void patchNonExistingNotlar() throws Exception {
        int databaseSizeBeforeUpdate = notlarRepository.findAll().size();
        notlar.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNotlarMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, notlar.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(notlar))
            )
            .andExpect(status().isBadRequest());

        // Validate the Notlar in the database
        List<Notlar> notlarList = notlarRepository.findAll();
        assertThat(notlarList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNotlar() throws Exception {
        int databaseSizeBeforeUpdate = notlarRepository.findAll().size();
        notlar.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNotlarMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(notlar))
            )
            .andExpect(status().isBadRequest());

        // Validate the Notlar in the database
        List<Notlar> notlarList = notlarRepository.findAll();
        assertThat(notlarList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNotlar() throws Exception {
        int databaseSizeBeforeUpdate = notlarRepository.findAll().size();
        notlar.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNotlarMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(notlar)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Notlar in the database
        List<Notlar> notlarList = notlarRepository.findAll();
        assertThat(notlarList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNotlar() throws Exception {
        // Initialize the database
        notlarRepository.saveAndFlush(notlar);

        int databaseSizeBeforeDelete = notlarRepository.findAll().size();

        // Delete the notlar
        restNotlarMockMvc
            .perform(delete(ENTITY_API_URL_ID, notlar.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Notlar> notlarList = notlarRepository.findAll();
        assertThat(notlarList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
