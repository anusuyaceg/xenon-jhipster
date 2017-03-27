package com.xenon.life.web.rest;

import com.xenon.life.XenonApp;

import com.xenon.life.domain.Xen;
import com.xenon.life.repository.XenRepository;
import com.xenon.life.service.XenService;
import com.xenon.life.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the XenResource REST controller.
 *
 * @see XenResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = XenonApp.class)
public class XenResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_TASK = false;
    private static final Boolean UPDATED_IS_TASK = true;

    private static final Boolean DEFAULT_IS_COMPLEATED = false;
    private static final Boolean UPDATED_IS_COMPLEATED = true;

    @Autowired
    private XenRepository xenRepository;

    @Autowired
    private XenService xenService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restXenMockMvc;

    private Xen xen;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        XenResource xenResource = new XenResource(xenService);
        this.restXenMockMvc = MockMvcBuilders.standaloneSetup(xenResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Xen createEntity(EntityManager em) {
        Xen xen = new Xen()
            .title(DEFAULT_TITLE)
            .description(DEFAULT_DESCRIPTION)
            .isTask(DEFAULT_IS_TASK)
            .isCompleated(DEFAULT_IS_COMPLEATED);
        return xen;
    }

    @Before
    public void initTest() {
        xen = createEntity(em);
    }

    @Test
    @Transactional
    public void createXen() throws Exception {
        int databaseSizeBeforeCreate = xenRepository.findAll().size();

        // Create the Xen
        restXenMockMvc.perform(post("/api/xens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(xen)))
            .andExpect(status().isCreated());

        // Validate the Xen in the database
        List<Xen> xenList = xenRepository.findAll();
        assertThat(xenList).hasSize(databaseSizeBeforeCreate + 1);
        Xen testXen = xenList.get(xenList.size() - 1);
        assertThat(testXen.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testXen.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testXen.isIsTask()).isEqualTo(DEFAULT_IS_TASK);
        assertThat(testXen.isIsCompleated()).isEqualTo(DEFAULT_IS_COMPLEATED);
    }

    @Test
    @Transactional
    public void createXenWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = xenRepository.findAll().size();

        // Create the Xen with an existing ID
        xen.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restXenMockMvc.perform(post("/api/xens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(xen)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Xen> xenList = xenRepository.findAll();
        assertThat(xenList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllXens() throws Exception {
        // Initialize the database
        xenRepository.saveAndFlush(xen);

        // Get all the xenList
        restXenMockMvc.perform(get("/api/xens?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(xen.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].isTask").value(hasItem(DEFAULT_IS_TASK.booleanValue())))
            .andExpect(jsonPath("$.[*].isCompleated").value(hasItem(DEFAULT_IS_COMPLEATED.booleanValue())));
    }

    @Test
    @Transactional
    public void getXen() throws Exception {
        // Initialize the database
        xenRepository.saveAndFlush(xen);

        // Get the xen
        restXenMockMvc.perform(get("/api/xens/{id}", xen.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(xen.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.isTask").value(DEFAULT_IS_TASK.booleanValue()))
            .andExpect(jsonPath("$.isCompleated").value(DEFAULT_IS_COMPLEATED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingXen() throws Exception {
        // Get the xen
        restXenMockMvc.perform(get("/api/xens/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateXen() throws Exception {
        // Initialize the database
        xenService.save(xen);

        int databaseSizeBeforeUpdate = xenRepository.findAll().size();

        // Update the xen
        Xen updatedXen = xenRepository.findOne(xen.getId());
        updatedXen
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .isTask(UPDATED_IS_TASK)
            .isCompleated(UPDATED_IS_COMPLEATED);

        restXenMockMvc.perform(put("/api/xens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedXen)))
            .andExpect(status().isOk());

        // Validate the Xen in the database
        List<Xen> xenList = xenRepository.findAll();
        assertThat(xenList).hasSize(databaseSizeBeforeUpdate);
        Xen testXen = xenList.get(xenList.size() - 1);
        assertThat(testXen.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testXen.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testXen.isIsTask()).isEqualTo(UPDATED_IS_TASK);
        assertThat(testXen.isIsCompleated()).isEqualTo(UPDATED_IS_COMPLEATED);
    }

    @Test
    @Transactional
    public void updateNonExistingXen() throws Exception {
        int databaseSizeBeforeUpdate = xenRepository.findAll().size();

        // Create the Xen

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restXenMockMvc.perform(put("/api/xens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(xen)))
            .andExpect(status().isCreated());

        // Validate the Xen in the database
        List<Xen> xenList = xenRepository.findAll();
        assertThat(xenList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteXen() throws Exception {
        // Initialize the database
        xenService.save(xen);

        int databaseSizeBeforeDelete = xenRepository.findAll().size();

        // Get the xen
        restXenMockMvc.perform(delete("/api/xens/{id}", xen.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Xen> xenList = xenRepository.findAll();
        assertThat(xenList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Xen.class);
    }
}
