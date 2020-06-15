package com.vfin.theconnect.web.rest;

import com.vfin.theconnect.TheconnectApp;
import com.vfin.theconnect.domain.AccountTC;
import com.vfin.theconnect.repository.AccountTCRepository;
import com.vfin.theconnect.service.AccountTCService;
import com.vfin.theconnect.service.dto.AccountTCDTO;
import com.vfin.theconnect.service.mapper.AccountTCMapper;
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


import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.vfin.theconnect.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link AccountTCResource} REST controller.
 */
@SpringBootTest(classes = TheconnectApp.class)
public class AccountTCResourceIT {

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DELETED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DELETED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private AccountTCRepository accountTCRepository;

    @Autowired
    private AccountTCMapper accountTCMapper;

    @Autowired
    private AccountTCService accountTCService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restAccountTCMockMvc;

    private AccountTC accountTC;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AccountTCResource accountTCResource = new AccountTCResource(accountTCService);
        this.restAccountTCMockMvc = MockMvcBuilders.standaloneSetup(accountTCResource)
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
    public static AccountTC createEntity() {
        AccountTC accountTC = new AccountTC()
            .email(DEFAULT_EMAIL)
            .password(DEFAULT_PASSWORD)
            .status(DEFAULT_STATUS)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT)
            .deletedAt(DEFAULT_DELETED_AT);
        return accountTC;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AccountTC createUpdatedEntity() {
        AccountTC accountTC = new AccountTC()
            .email(UPDATED_EMAIL)
            .password(UPDATED_PASSWORD)
            .status(UPDATED_STATUS)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .deletedAt(UPDATED_DELETED_AT);
        return accountTC;
    }

    @BeforeEach
    public void initTest() {
        accountTCRepository.deleteAll();
        accountTC = createEntity();
    }

    @Test
    public void createAccountTC() throws Exception {
        int databaseSizeBeforeCreate = accountTCRepository.findAll().size();

        // Create the AccountTC
        AccountTCDTO accountTCDTO = accountTCMapper.toDto(accountTC);
        restAccountTCMockMvc.perform(post("/api/account-tcs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accountTCDTO)))
            .andExpect(status().isCreated());

        // Validate the AccountTC in the database
        List<AccountTC> accountTCList = accountTCRepository.findAll();
        assertThat(accountTCList).hasSize(databaseSizeBeforeCreate + 1);
        AccountTC testAccountTC = accountTCList.get(accountTCList.size() - 1);
        assertThat(testAccountTC.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testAccountTC.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testAccountTC.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testAccountTC.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testAccountTC.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testAccountTC.getDeletedAt()).isEqualTo(DEFAULT_DELETED_AT);
    }

    @Test
    public void createAccountTCWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = accountTCRepository.findAll().size();

        // Create the AccountTC with an existing ID
        accountTC.setId("existing_id");
        AccountTCDTO accountTCDTO = accountTCMapper.toDto(accountTC);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAccountTCMockMvc.perform(post("/api/account-tcs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accountTCDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AccountTC in the database
        List<AccountTC> accountTCList = accountTCRepository.findAll();
        assertThat(accountTCList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = accountTCRepository.findAll().size();
        // set the field null
        accountTC.setEmail(null);

        // Create the AccountTC, which fails.
        AccountTCDTO accountTCDTO = accountTCMapper.toDto(accountTC);

        restAccountTCMockMvc.perform(post("/api/account-tcs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accountTCDTO)))
            .andExpect(status().isBadRequest());

        List<AccountTC> accountTCList = accountTCRepository.findAll();
        assertThat(accountTCList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkPasswordIsRequired() throws Exception {
        int databaseSizeBeforeTest = accountTCRepository.findAll().size();
        // set the field null
        accountTC.setPassword(null);

        // Create the AccountTC, which fails.
        AccountTCDTO accountTCDTO = accountTCMapper.toDto(accountTC);

        restAccountTCMockMvc.perform(post("/api/account-tcs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accountTCDTO)))
            .andExpect(status().isBadRequest());

        List<AccountTC> accountTCList = accountTCRepository.findAll();
        assertThat(accountTCList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = accountTCRepository.findAll().size();
        // set the field null
        accountTC.setStatus(null);

        // Create the AccountTC, which fails.
        AccountTCDTO accountTCDTO = accountTCMapper.toDto(accountTC);

        restAccountTCMockMvc.perform(post("/api/account-tcs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accountTCDTO)))
            .andExpect(status().isBadRequest());

        List<AccountTC> accountTCList = accountTCRepository.findAll();
        assertThat(accountTCList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = accountTCRepository.findAll().size();
        // set the field null
        accountTC.setCreatedAt(null);

        // Create the AccountTC, which fails.
        AccountTCDTO accountTCDTO = accountTCMapper.toDto(accountTC);

        restAccountTCMockMvc.perform(post("/api/account-tcs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accountTCDTO)))
            .andExpect(status().isBadRequest());

        List<AccountTC> accountTCList = accountTCRepository.findAll();
        assertThat(accountTCList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkUpdatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = accountTCRepository.findAll().size();
        // set the field null
        accountTC.setUpdatedAt(null);

        // Create the AccountTC, which fails.
        AccountTCDTO accountTCDTO = accountTCMapper.toDto(accountTC);

        restAccountTCMockMvc.perform(post("/api/account-tcs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accountTCDTO)))
            .andExpect(status().isBadRequest());

        List<AccountTC> accountTCList = accountTCRepository.findAll();
        assertThat(accountTCList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkDeletedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = accountTCRepository.findAll().size();
        // set the field null
        accountTC.setDeletedAt(null);

        // Create the AccountTC, which fails.
        AccountTCDTO accountTCDTO = accountTCMapper.toDto(accountTC);

        restAccountTCMockMvc.perform(post("/api/account-tcs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accountTCDTO)))
            .andExpect(status().isBadRequest());

        List<AccountTC> accountTCList = accountTCRepository.findAll();
        assertThat(accountTCList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllAccountTCS() throws Exception {
        // Initialize the database
        accountTCRepository.save(accountTC);

        // Get all the accountTCList
        restAccountTCMockMvc.perform(get("/api/account-tcs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(accountTC.getId())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].deletedAt").value(hasItem(DEFAULT_DELETED_AT.toString())));
    }
    
    @Test
    public void getAccountTC() throws Exception {
        // Initialize the database
        accountTCRepository.save(accountTC);

        // Get the accountTC
        restAccountTCMockMvc.perform(get("/api/account-tcs/{id}", accountTC.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(accountTC.getId()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.deletedAt").value(DEFAULT_DELETED_AT.toString()));
    }

    @Test
    public void getNonExistingAccountTC() throws Exception {
        // Get the accountTC
        restAccountTCMockMvc.perform(get("/api/account-tcs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateAccountTC() throws Exception {
        // Initialize the database
        accountTCRepository.save(accountTC);

        int databaseSizeBeforeUpdate = accountTCRepository.findAll().size();

        // Update the accountTC
        AccountTC updatedAccountTC = accountTCRepository.findById(accountTC.getId()).get();
        updatedAccountTC
            .email(UPDATED_EMAIL)
            .password(UPDATED_PASSWORD)
            .status(UPDATED_STATUS)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .deletedAt(UPDATED_DELETED_AT);
        AccountTCDTO accountTCDTO = accountTCMapper.toDto(updatedAccountTC);

        restAccountTCMockMvc.perform(put("/api/account-tcs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accountTCDTO)))
            .andExpect(status().isOk());

        // Validate the AccountTC in the database
        List<AccountTC> accountTCList = accountTCRepository.findAll();
        assertThat(accountTCList).hasSize(databaseSizeBeforeUpdate);
        AccountTC testAccountTC = accountTCList.get(accountTCList.size() - 1);
        assertThat(testAccountTC.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testAccountTC.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testAccountTC.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testAccountTC.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testAccountTC.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testAccountTC.getDeletedAt()).isEqualTo(UPDATED_DELETED_AT);
    }

    @Test
    public void updateNonExistingAccountTC() throws Exception {
        int databaseSizeBeforeUpdate = accountTCRepository.findAll().size();

        // Create the AccountTC
        AccountTCDTO accountTCDTO = accountTCMapper.toDto(accountTC);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAccountTCMockMvc.perform(put("/api/account-tcs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accountTCDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AccountTC in the database
        List<AccountTC> accountTCList = accountTCRepository.findAll();
        assertThat(accountTCList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteAccountTC() throws Exception {
        // Initialize the database
        accountTCRepository.save(accountTC);

        int databaseSizeBeforeDelete = accountTCRepository.findAll().size();

        // Delete the accountTC
        restAccountTCMockMvc.perform(delete("/api/account-tcs/{id}", accountTC.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AccountTC> accountTCList = accountTCRepository.findAll();
        assertThat(accountTCList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AccountTC.class);
        AccountTC accountTC1 = new AccountTC();
        accountTC1.setId("id1");
        AccountTC accountTC2 = new AccountTC();
        accountTC2.setId(accountTC1.getId());
        assertThat(accountTC1).isEqualTo(accountTC2);
        accountTC2.setId("id2");
        assertThat(accountTC1).isNotEqualTo(accountTC2);
        accountTC1.setId(null);
        assertThat(accountTC1).isNotEqualTo(accountTC2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AccountTCDTO.class);
        AccountTCDTO accountTCDTO1 = new AccountTCDTO();
        accountTCDTO1.setId("id1");
        AccountTCDTO accountTCDTO2 = new AccountTCDTO();
        assertThat(accountTCDTO1).isNotEqualTo(accountTCDTO2);
        accountTCDTO2.setId(accountTCDTO1.getId());
        assertThat(accountTCDTO1).isEqualTo(accountTCDTO2);
        accountTCDTO2.setId("id2");
        assertThat(accountTCDTO1).isNotEqualTo(accountTCDTO2);
        accountTCDTO1.setId(null);
        assertThat(accountTCDTO1).isNotEqualTo(accountTCDTO2);
    }
}
