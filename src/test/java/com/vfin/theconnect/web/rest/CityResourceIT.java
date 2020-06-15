package com.vfin.theconnect.web.rest;

import com.vfin.theconnect.TheconnectApp;
import com.vfin.theconnect.domain.City;
import com.vfin.theconnect.repository.CityRepository;
import com.vfin.theconnect.service.CityService;
import com.vfin.theconnect.service.dto.CityDTO;
import com.vfin.theconnect.service.mapper.CityMapper;
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
 * Integration tests for the {@link CityResource} REST controller.
 */
@SpringBootTest(classes = TheconnectApp.class)
public class CityResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private CityMapper cityMapper;

    @Autowired
    private CityService cityService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restCityMockMvc;

    private City city;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CityResource cityResource = new CityResource(cityService);
        this.restCityMockMvc = MockMvcBuilders.standaloneSetup(cityResource)
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
    public static City createEntity() {
        City city = new City()
            .name(DEFAULT_NAME);
        return city;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static City createUpdatedEntity() {
        City city = new City()
            .name(UPDATED_NAME);
        return city;
    }

    @BeforeEach
    public void initTest() {
        cityRepository.deleteAll();
        city = createEntity();
    }

    @Test
    public void createCity() throws Exception {
        int databaseSizeBeforeCreate = cityRepository.findAll().size();

        // Create the City
        CityDTO cityDTO = cityMapper.toDto(city);
        restCityMockMvc.perform(post("/api/cities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cityDTO)))
            .andExpect(status().isCreated());

        // Validate the City in the database
        List<City> cityList = cityRepository.findAll();
        assertThat(cityList).hasSize(databaseSizeBeforeCreate + 1);
        City testCity = cityList.get(cityList.size() - 1);
        assertThat(testCity.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    public void createCityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cityRepository.findAll().size();

        // Create the City with an existing ID
        city.setId("existing_id");
        CityDTO cityDTO = cityMapper.toDto(city);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCityMockMvc.perform(post("/api/cities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the City in the database
        List<City> cityList = cityRepository.findAll();
        assertThat(cityList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = cityRepository.findAll().size();
        // set the field null
        city.setName(null);

        // Create the City, which fails.
        CityDTO cityDTO = cityMapper.toDto(city);

        restCityMockMvc.perform(post("/api/cities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cityDTO)))
            .andExpect(status().isBadRequest());

        List<City> cityList = cityRepository.findAll();
        assertThat(cityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllCities() throws Exception {
        // Initialize the database
        cityRepository.save(city);

        // Get all the cityList
        restCityMockMvc.perform(get("/api/cities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(city.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    public void getCity() throws Exception {
        // Initialize the database
        cityRepository.save(city);

        // Get the city
        restCityMockMvc.perform(get("/api/cities/{id}", city.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(city.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    public void getNonExistingCity() throws Exception {
        // Get the city
        restCityMockMvc.perform(get("/api/cities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateCity() throws Exception {
        // Initialize the database
        cityRepository.save(city);

        int databaseSizeBeforeUpdate = cityRepository.findAll().size();

        // Update the city
        City updatedCity = cityRepository.findById(city.getId()).get();
        updatedCity
            .name(UPDATED_NAME);
        CityDTO cityDTO = cityMapper.toDto(updatedCity);

        restCityMockMvc.perform(put("/api/cities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cityDTO)))
            .andExpect(status().isOk());

        // Validate the City in the database
        List<City> cityList = cityRepository.findAll();
        assertThat(cityList).hasSize(databaseSizeBeforeUpdate);
        City testCity = cityList.get(cityList.size() - 1);
        assertThat(testCity.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    public void updateNonExistingCity() throws Exception {
        int databaseSizeBeforeUpdate = cityRepository.findAll().size();

        // Create the City
        CityDTO cityDTO = cityMapper.toDto(city);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCityMockMvc.perform(put("/api/cities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the City in the database
        List<City> cityList = cityRepository.findAll();
        assertThat(cityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteCity() throws Exception {
        // Initialize the database
        cityRepository.save(city);

        int databaseSizeBeforeDelete = cityRepository.findAll().size();

        // Delete the city
        restCityMockMvc.perform(delete("/api/cities/{id}", city.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<City> cityList = cityRepository.findAll();
        assertThat(cityList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(City.class);
        City city1 = new City();
        city1.setId("id1");
        City city2 = new City();
        city2.setId(city1.getId());
        assertThat(city1).isEqualTo(city2);
        city2.setId("id2");
        assertThat(city1).isNotEqualTo(city2);
        city1.setId(null);
        assertThat(city1).isNotEqualTo(city2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CityDTO.class);
        CityDTO cityDTO1 = new CityDTO();
        cityDTO1.setId("id1");
        CityDTO cityDTO2 = new CityDTO();
        assertThat(cityDTO1).isNotEqualTo(cityDTO2);
        cityDTO2.setId(cityDTO1.getId());
        assertThat(cityDTO1).isEqualTo(cityDTO2);
        cityDTO2.setId("id2");
        assertThat(cityDTO1).isNotEqualTo(cityDTO2);
        cityDTO1.setId(null);
        assertThat(cityDTO1).isNotEqualTo(cityDTO2);
    }
}
