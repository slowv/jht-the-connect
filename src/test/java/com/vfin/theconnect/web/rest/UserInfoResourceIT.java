package com.vfin.theconnect.web.rest;

import com.vfin.theconnect.TheconnectApp;
import com.vfin.theconnect.domain.UserInfo;
import com.vfin.theconnect.repository.UserInfoRepository;
import com.vfin.theconnect.service.UserInfoService;
import com.vfin.theconnect.service.dto.UserInfoDTO;
import com.vfin.theconnect.service.mapper.UserInfoMapper;
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
 * Integration tests for the {@link UserInfoResource} REST controller.
 */
@SpringBootTest(classes = TheconnectApp.class)
public class UserInfoResourceIT {

    private static final Instant DEFAULT_DOB = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DOB = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Gender DEFAULT_GENDER = Gender.MALE;
    private static final Gender UPDATED_GENDER = Gender.FEMALE;

    private static final Integer DEFAULT_AGE = 1;
    private static final Integer UPDATED_AGE = 2;

    private static final String DEFAULT_INTRODUCE = "AAAAAAAAAA";
    private static final String UPDATED_INTRODUCE = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "060553573";
    private static final String UPDATED_PHONE = "054068507";

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restUserInfoMockMvc;

    private UserInfo userInfo;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UserInfoResource userInfoResource = new UserInfoResource(userInfoService);
        this.restUserInfoMockMvc = MockMvcBuilders.standaloneSetup(userInfoResource)
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
    public static UserInfo createEntity() {
        UserInfo userInfo = new UserInfo()
            .dob(DEFAULT_DOB)
            .gender(DEFAULT_GENDER)
            .age(DEFAULT_AGE)
            .introduce(DEFAULT_INTRODUCE)
            .phone(DEFAULT_PHONE);
        return userInfo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserInfo createUpdatedEntity() {
        UserInfo userInfo = new UserInfo()
            .dob(UPDATED_DOB)
            .gender(UPDATED_GENDER)
            .age(UPDATED_AGE)
            .introduce(UPDATED_INTRODUCE)
            .phone(UPDATED_PHONE);
        return userInfo;
    }

    @BeforeEach
    public void initTest() {
        userInfoRepository.deleteAll();
        userInfo = createEntity();
    }

    @Test
    public void createUserInfo() throws Exception {
        int databaseSizeBeforeCreate = userInfoRepository.findAll().size();

        // Create the UserInfo
        UserInfoDTO userInfoDTO = userInfoMapper.toDto(userInfo);
        restUserInfoMockMvc.perform(post("/api/user-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userInfoDTO)))
            .andExpect(status().isCreated());

        // Validate the UserInfo in the database
        List<UserInfo> userInfoList = userInfoRepository.findAll();
        assertThat(userInfoList).hasSize(databaseSizeBeforeCreate + 1);
        UserInfo testUserInfo = userInfoList.get(userInfoList.size() - 1);
        assertThat(testUserInfo.getDob()).isEqualTo(DEFAULT_DOB);
        assertThat(testUserInfo.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testUserInfo.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testUserInfo.getIntroduce()).isEqualTo(DEFAULT_INTRODUCE);
        assertThat(testUserInfo.getPhone()).isEqualTo(DEFAULT_PHONE);
    }

    @Test
    public void createUserInfoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userInfoRepository.findAll().size();

        // Create the UserInfo with an existing ID
        userInfo.setId("existing_id");
        UserInfoDTO userInfoDTO = userInfoMapper.toDto(userInfo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserInfoMockMvc.perform(post("/api/user-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userInfoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserInfo in the database
        List<UserInfo> userInfoList = userInfoRepository.findAll();
        assertThat(userInfoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void checkDobIsRequired() throws Exception {
        int databaseSizeBeforeTest = userInfoRepository.findAll().size();
        // set the field null
        userInfo.setDob(null);

        // Create the UserInfo, which fails.
        UserInfoDTO userInfoDTO = userInfoMapper.toDto(userInfo);

        restUserInfoMockMvc.perform(post("/api/user-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userInfoDTO)))
            .andExpect(status().isBadRequest());

        List<UserInfo> userInfoList = userInfoRepository.findAll();
        assertThat(userInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkPhoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = userInfoRepository.findAll().size();
        // set the field null
        userInfo.setPhone(null);

        // Create the UserInfo, which fails.
        UserInfoDTO userInfoDTO = userInfoMapper.toDto(userInfo);

        restUserInfoMockMvc.perform(post("/api/user-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userInfoDTO)))
            .andExpect(status().isBadRequest());

        List<UserInfo> userInfoList = userInfoRepository.findAll();
        assertThat(userInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllUserInfos() throws Exception {
        // Initialize the database
        userInfoRepository.save(userInfo);

        // Get all the userInfoList
        restUserInfoMockMvc.perform(get("/api/user-infos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userInfo.getId())))
            .andExpect(jsonPath("$.[*].dob").value(hasItem(DEFAULT_DOB.toString())))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)))
            .andExpect(jsonPath("$.[*].introduce").value(hasItem(DEFAULT_INTRODUCE.toString())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)));
    }

    @Test
    public void getUserInfo() throws Exception {
        // Initialize the database
        userInfoRepository.save(userInfo);

        // Get the userInfo
        restUserInfoMockMvc.perform(get("/api/user-infos/{id}", userInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userInfo.getId()))
            .andExpect(jsonPath("$.dob").value(DEFAULT_DOB.toString()))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE))
            .andExpect(jsonPath("$.introduce").value(DEFAULT_INTRODUCE.toString()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE));
    }

    @Test
    public void getNonExistingUserInfo() throws Exception {
        // Get the userInfo
        restUserInfoMockMvc.perform(get("/api/user-infos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateUserInfo() throws Exception {
        // Initialize the database
        userInfoRepository.save(userInfo);

        int databaseSizeBeforeUpdate = userInfoRepository.findAll().size();

        // Update the userInfo
        UserInfo updatedUserInfo = userInfoRepository.findById(userInfo.getId()).get();
        updatedUserInfo
            .dob(UPDATED_DOB)
            .gender(UPDATED_GENDER)
            .age(UPDATED_AGE)
            .introduce(UPDATED_INTRODUCE)
            .phone(UPDATED_PHONE);
        UserInfoDTO userInfoDTO = userInfoMapper.toDto(updatedUserInfo);

        restUserInfoMockMvc.perform(put("/api/user-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userInfoDTO)))
            .andExpect(status().isOk());

        // Validate the UserInfo in the database
        List<UserInfo> userInfoList = userInfoRepository.findAll();
        assertThat(userInfoList).hasSize(databaseSizeBeforeUpdate);
        UserInfo testUserInfo = userInfoList.get(userInfoList.size() - 1);
        assertThat(testUserInfo.getDob()).isEqualTo(UPDATED_DOB);
        assertThat(testUserInfo.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testUserInfo.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testUserInfo.getIntroduce()).isEqualTo(UPDATED_INTRODUCE);
        assertThat(testUserInfo.getPhone()).isEqualTo(UPDATED_PHONE);
    }

    @Test
    public void updateNonExistingUserInfo() throws Exception {
        int databaseSizeBeforeUpdate = userInfoRepository.findAll().size();

        // Create the UserInfo
        UserInfoDTO userInfoDTO = userInfoMapper.toDto(userInfo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserInfoMockMvc.perform(put("/api/user-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userInfoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserInfo in the database
        List<UserInfo> userInfoList = userInfoRepository.findAll();
        assertThat(userInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteUserInfo() throws Exception {
        // Initialize the database
        userInfoRepository.save(userInfo);

        int databaseSizeBeforeDelete = userInfoRepository.findAll().size();

        // Delete the userInfo
        restUserInfoMockMvc.perform(delete("/api/user-infos/{id}", userInfo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserInfo> userInfoList = userInfoRepository.findAll();
        assertThat(userInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserInfo.class);
        UserInfo userInfo1 = new UserInfo();
        userInfo1.setId("id1");
        UserInfo userInfo2 = new UserInfo();
        userInfo2.setId(userInfo1.getId());
        assertThat(userInfo1).isEqualTo(userInfo2);
        userInfo2.setId("id2");
        assertThat(userInfo1).isNotEqualTo(userInfo2);
        userInfo1.setId(null);
        assertThat(userInfo1).isNotEqualTo(userInfo2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserInfoDTO.class);
        UserInfoDTO userInfoDTO1 = new UserInfoDTO();
        userInfoDTO1.setId("id1");
        UserInfoDTO userInfoDTO2 = new UserInfoDTO();
        assertThat(userInfoDTO1).isNotEqualTo(userInfoDTO2);
        userInfoDTO2.setId(userInfoDTO1.getId());
        assertThat(userInfoDTO1).isEqualTo(userInfoDTO2);
        userInfoDTO2.setId("id2");
        assertThat(userInfoDTO1).isNotEqualTo(userInfoDTO2);
        userInfoDTO1.setId(null);
        assertThat(userInfoDTO1).isNotEqualTo(userInfoDTO2);
    }
}
