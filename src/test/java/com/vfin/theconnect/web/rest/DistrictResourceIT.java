package com.vfin.theconnect.web.rest;

import com.vfin.theconnect.TheconnectApp;
import com.vfin.theconnect.domain.District;
import com.vfin.theconnect.repository.DistrictRepository;
import com.vfin.theconnect.service.DistrictService;
import com.vfin.theconnect.service.dto.DistrictDTO;
import com.vfin.theconnect.service.mapper.DistrictMapper;
import com.vfin.theconnect.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Validator;


import java.util.List;

import static com.vfin.theconnect.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link DistrictResource} REST controller.
 */
@SpringBootTest(classes = TheconnectApp.class)
public class DistrictResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private DistrictRepository districtRepository;

    @Autowired
    private DistrictMapper districtMapper;

    @Autowired
    private DistrictService districtService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restDistrictMockMvc;

    private District district;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DistrictResource districtResource = new DistrictResource(districtService);
        this.restDistrictMockMvc = MockMvcBuilders.standaloneSetup(districtResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static District createEntity() {
        District district = new District()
            .name(DEFAULT_NAME);
        return district;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static District createUpdatedEntity() {
        District district = new District()
            .name(UPDATED_NAME);
        return district;
    }

    @BeforeEach
    public void initTest() {
        districtRepository.deleteAll();
        district = createEntity();
    }

    @Test
    public void createDistrict() throws Exception {
        int databaseSizeBeforeCreate = districtRepository.findAll().size();

        // Create the District
        DistrictDTO districtDTO = districtMapper.toDto(district);
        restDistrictMockMvc.perform(post("/api/districts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(districtDTO)))
            .andExpect(status().isCreated());

        // Validate the District in the database
        List<District> districtList = districtRepository.findAll();
        assertThat(districtList).hasSize(databaseSizeBeforeCreate + 1);
        District testDistrict = districtList.get(districtList.size() - 1);
        assertThat(testDistrict.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    public void createDistrictWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = districtRepository.findAll().size();

        // Create the District with an existing ID
        district.setId("existing_id");
        DistrictDTO districtDTO = districtMapper.toDto(district);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDistrictMockMvc.perform(post("/api/districts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(districtDTO)))
            .andExpect(status().isBadRequest());

        // Validate the District in the database
        List<District> districtList = districtRepository.findAll();
        assertThat(districtList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = districtRepository.findAll().size();
        // set the field null
        district.setName(null);

        // Create the District, which fails.
        DistrictDTO districtDTO = districtMapper.toDto(district);

        restDistrictMockMvc.perform(post("/api/districts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(districtDTO)))
            .andExpect(status().isBadRequest());

        List<District> districtList = districtRepository.findAll();
        assertThat(districtList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllDistricts() throws Exception {
        // Initialize the database
        districtRepository.save(district);

        // Get all the districtList
        restDistrictMockMvc.perform(get("/api/districts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(district.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    public void getDistrict() throws Exception {
        // Initialize the database
        districtRepository.save(district);

        // Get the district
        restDistrictMockMvc.perform(get("/api/districts/{id}", district.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(district.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    public void getNonExistingDistrict() throws Exception {
        // Get the district
        restDistrictMockMvc.perform(get("/api/districts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateDistrict() throws Exception {
        // Initialize the database
        districtRepository.save(district);

        int databaseSizeBeforeUpdate = districtRepository.findAll().size();

        // Update the district
        District updatedDistrict = districtRepository.findById(district.getId()).get();
        updatedDistrict
            .name(UPDATED_NAME);
        DistrictDTO districtDTO = districtMapper.toDto(updatedDistrict);

        restDistrictMockMvc.perform(put("/api/districts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(districtDTO)))
            .andExpect(status().isOk());

        // Validate the District in the database
        List<District> districtList = districtRepository.findAll();
        assertThat(districtList).hasSize(databaseSizeBeforeUpdate);
        District testDistrict = districtList.get(districtList.size() - 1);
        assertThat(testDistrict.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    public void updateNonExistingDistrict() throws Exception {
        int databaseSizeBeforeUpdate = districtRepository.findAll().size();

        // Create the District
        DistrictDTO districtDTO = districtMapper.toDto(district);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDistrictMockMvc.perform(put("/api/districts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(districtDTO)))
            .andExpect(status().isBadRequest());

        // Validate the District in the database
        List<District> districtList = districtRepository.findAll();
        assertThat(districtList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteDistrict() throws Exception {
        // Initialize the database
        districtRepository.save(district);

        int databaseSizeBeforeDelete = districtRepository.findAll().size();

        // Delete the district
        restDistrictMockMvc.perform(delete("/api/districts/{id}", district.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<District> districtList = districtRepository.findAll();
        assertThat(districtList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(District.class);
        District district1 = new District();
        district1.setId("id1");
        District district2 = new District();
        district2.setId(district1.getId());
        assertThat(district1).isEqualTo(district2);
        district2.setId("id2");
        assertThat(district1).isNotEqualTo(district2);
        district1.setId(null);
        assertThat(district1).isNotEqualTo(district2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DistrictDTO.class);
        DistrictDTO districtDTO1 = new DistrictDTO();
        districtDTO1.setId("id1");
        DistrictDTO districtDTO2 = new DistrictDTO();
        assertThat(districtDTO1).isNotEqualTo(districtDTO2);
        districtDTO2.setId(districtDTO1.getId());
        assertThat(districtDTO1).isEqualTo(districtDTO2);
        districtDTO2.setId("id2");
        assertThat(districtDTO1).isNotEqualTo(districtDTO2);
        districtDTO1.setId(null);
        assertThat(districtDTO1).isNotEqualTo(districtDTO2);
    }
}
