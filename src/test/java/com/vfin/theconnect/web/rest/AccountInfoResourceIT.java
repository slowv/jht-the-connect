package com.vfin.theconnect.web.rest;

import com.vfin.theconnect.TheconnectApp;
import com.vfin.theconnect.domain.AccountInfo;
import com.vfin.theconnect.repository.AccountInfoRepository;
import com.vfin.theconnect.service.AccountInfoService;
import com.vfin.theconnect.service.dto.AccountInfoDTO;
import com.vfin.theconnect.service.mapper.AccountInfoMapper;
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
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;


import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.vfin.theconnect.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.vfin.theconnect.domain.enumeration.Gender;
/**
 * Integration tests for the {@link AccountInfoResource} REST controller.
 */
@SpringBootTest(classes = TheconnectApp.class)
public class AccountInfoResourceIT {

    private static final Instant DEFAULT_DOB = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DOB = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Gender DEFAULT_GENDER = Gender.MALE;
    private static final Gender UPDATED_GENDER = Gender.FEMALE;

    private static final Integer DEFAULT_AGE = 1;
    private static final Integer UPDATED_AGE = 2;

    private static final String DEFAULT_INTRODUCE = "AAAAAAAAAA";
    private static final String UPDATED_INTRODUCE = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "011774753";
    private static final String UPDATED_PHONE = "002452130";

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    @Autowired
    private AccountInfoRepository accountInfoRepository;

    @Autowired
    private AccountInfoMapper accountInfoMapper;

    @Autowired
    private AccountInfoService accountInfoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restAccountInfoMockMvc;

    private AccountInfo accountInfo;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AccountInfoResource accountInfoResource = new AccountInfoResource(accountInfoService);
        this.restAccountInfoMockMvc = MockMvcBuilders.standaloneSetup(accountInfoResource)
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
    public static AccountInfo createEntity() {
        AccountInfo accountInfo = new AccountInfo()
            .dob(DEFAULT_DOB)
            .gender(DEFAULT_GENDER)
            .age(DEFAULT_AGE)
            .introduce(DEFAULT_INTRODUCE)
            .phone(DEFAULT_PHONE)
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME);
        return accountInfo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AccountInfo createUpdatedEntity() {
        AccountInfo accountInfo = new AccountInfo()
            .dob(UPDATED_DOB)
            .gender(UPDATED_GENDER)
            .age(UPDATED_AGE)
            .introduce(UPDATED_INTRODUCE)
            .phone(UPDATED_PHONE)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME);
        return accountInfo;
    }

    @BeforeEach
    public void initTest() {
        accountInfoRepository.deleteAll();
        accountInfo = createEntity();
    }

    @Test
    public void createAccountInfo() throws Exception {
        int databaseSizeBeforeCreate = accountInfoRepository.findAll().size();

        // Create the AccountInfo
        AccountInfoDTO accountInfoDTO = accountInfoMapper.toDto(accountInfo);
        restAccountInfoMockMvc.perform(post("/api/account-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accountInfoDTO)))
            .andExpect(status().isCreated());

        // Validate the AccountInfo in the database
        List<AccountInfo> accountInfoList = accountInfoRepository.findAll();
        assertThat(accountInfoList).hasSize(databaseSizeBeforeCreate + 1);
        AccountInfo testAccountInfo = accountInfoList.get(accountInfoList.size() - 1);
        assertThat(testAccountInfo.getDob()).isEqualTo(DEFAULT_DOB);
        assertThat(testAccountInfo.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testAccountInfo.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testAccountInfo.getIntroduce()).isEqualTo(DEFAULT_INTRODUCE);
        assertThat(testAccountInfo.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testAccountInfo.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testAccountInfo.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
    }

    @Test
    public void createAccountInfoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = accountInfoRepository.findAll().size();

        // Create the AccountInfo with an existing ID
        accountInfo.setId("existing_id");
        AccountInfoDTO accountInfoDTO = accountInfoMapper.toDto(accountInfo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAccountInfoMockMvc.perform(post("/api/account-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accountInfoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AccountInfo in the database
        List<AccountInfo> accountInfoList = accountInfoRepository.findAll();
        assertThat(accountInfoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void checkDobIsRequired() throws Exception {
        int databaseSizeBeforeTest = accountInfoRepository.findAll().size();
        // set the field null
        accountInfo.setDob(null);

        // Create the AccountInfo, which fails.
        AccountInfoDTO accountInfoDTO = accountInfoMapper.toDto(accountInfo);

        restAccountInfoMockMvc.perform(post("/api/account-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accountInfoDTO)))
            .andExpect(status().isBadRequest());

        List<AccountInfo> accountInfoList = accountInfoRepository.findAll();
        assertThat(accountInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkPhoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = accountInfoRepository.findAll().size();
        // set the field null
        accountInfo.setPhone(null);

        // Create the AccountInfo, which fails.
        AccountInfoDTO accountInfoDTO = accountInfoMapper.toDto(accountInfo);

        restAccountInfoMockMvc.perform(post("/api/account-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accountInfoDTO)))
            .andExpect(status().isBadRequest());

        List<AccountInfo> accountInfoList = accountInfoRepository.findAll();
        assertThat(accountInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllAccountInfos() throws Exception {
        // Initialize the database
        accountInfoRepository.save(accountInfo);

        // Get all the accountInfoList
        restAccountInfoMockMvc.perform(get("/api/account-infos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(accountInfo.getId())))
            .andExpect(jsonPath("$.[*].dob").value(hasItem(DEFAULT_DOB.toString())))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)))
            .andExpect(jsonPath("$.[*].introduce").value(hasItem(DEFAULT_INTRODUCE.toString())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)));
    }
    
    @Test
    public void getAccountInfo() throws Exception {
        // Initialize the database
        accountInfoRepository.save(accountInfo);

        // Get the accountInfo
        restAccountInfoMockMvc.perform(get("/api/account-infos/{id}", accountInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(accountInfo.getId()))
            .andExpect(jsonPath("$.dob").value(DEFAULT_DOB.toString()))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE))
            .andExpect(jsonPath("$.introduce").value(DEFAULT_INTRODUCE.toString()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME));
    }

    @Test
    public void getNonExistingAccountInfo() throws Exception {
        // Get the accountInfo
        restAccountInfoMockMvc.perform(get("/api/account-infos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateAccountInfo() throws Exception {
        // Initialize the database
        accountInfoRepository.save(accountInfo);

        int databaseSizeBeforeUpdate = accountInfoRepository.findAll().size();

        // Update the accountInfo
        AccountInfo updatedAccountInfo = accountInfoRepository.findById(accountInfo.getId()).get();
        updatedAccountInfo
            .dob(UPDATED_DOB)
            .gender(UPDATED_GENDER)
            .age(UPDATED_AGE)
            .introduce(UPDATED_INTRODUCE)
            .phone(UPDATED_PHONE)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME);
        AccountInfoDTO accountInfoDTO = accountInfoMapper.toDto(updatedAccountInfo);

        restAccountInfoMockMvc.perform(put("/api/account-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accountInfoDTO)))
            .andExpect(status().isOk());

        // Validate the AccountInfo in the database
        List<AccountInfo> accountInfoList = accountInfoRepository.findAll();
        assertThat(accountInfoList).hasSize(databaseSizeBeforeUpdate);
        AccountInfo testAccountInfo = accountInfoList.get(accountInfoList.size() - 1);
        assertThat(testAccountInfo.getDob()).isEqualTo(UPDATED_DOB);
        assertThat(testAccountInfo.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testAccountInfo.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testAccountInfo.getIntroduce()).isEqualTo(UPDATED_INTRODUCE);
        assertThat(testAccountInfo.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testAccountInfo.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testAccountInfo.getLastName()).isEqualTo(UPDATED_LAST_NAME);
    }

    @Test
    public void updateNonExistingAccountInfo() throws Exception {
        int databaseSizeBeforeUpdate = accountInfoRepository.findAll().size();

        // Create the AccountInfo
        AccountInfoDTO accountInfoDTO = accountInfoMapper.toDto(accountInfo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAccountInfoMockMvc.perform(put("/api/account-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accountInfoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AccountInfo in the database
        List<AccountInfo> accountInfoList = accountInfoRepository.findAll();
        assertThat(accountInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteAccountInfo() throws Exception {
        // Initialize the database
        accountInfoRepository.save(accountInfo);

        int databaseSizeBeforeDelete = accountInfoRepository.findAll().size();

        // Delete the accountInfo
        restAccountInfoMockMvc.perform(delete("/api/account-infos/{id}", accountInfo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AccountInfo> accountInfoList = accountInfoRepository.findAll();
        assertThat(accountInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AccountInfo.class);
        AccountInfo accountInfo1 = new AccountInfo();
        accountInfo1.setId("id1");
        AccountInfo accountInfo2 = new AccountInfo();
        accountInfo2.setId(accountInfo1.getId());
        assertThat(accountInfo1).isEqualTo(accountInfo2);
        accountInfo2.setId("id2");
        assertThat(accountInfo1).isNotEqualTo(accountInfo2);
        accountInfo1.setId(null);
        assertThat(accountInfo1).isNotEqualTo(accountInfo2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AccountInfoDTO.class);
        AccountInfoDTO accountInfoDTO1 = new AccountInfoDTO();
        accountInfoDTO1.setId("id1");
        AccountInfoDTO accountInfoDTO2 = new AccountInfoDTO();
        assertThat(accountInfoDTO1).isNotEqualTo(accountInfoDTO2);
        accountInfoDTO2.setId(accountInfoDTO1.getId());
        assertThat(accountInfoDTO1).isEqualTo(accountInfoDTO2);
        accountInfoDTO2.setId("id2");
        assertThat(accountInfoDTO1).isNotEqualTo(accountInfoDTO2);
        accountInfoDTO1.setId(null);
        assertThat(accountInfoDTO1).isNotEqualTo(accountInfoDTO2);
    }
}
