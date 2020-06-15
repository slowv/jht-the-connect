package com.vfin.theconnect.web.rest;

import com.vfin.theconnect.TheconnectApp;
import com.vfin.theconnect.domain.Friend;
import com.vfin.theconnect.repository.FriendRepository;
import com.vfin.theconnect.service.FriendService;
import com.vfin.theconnect.service.dto.FriendDTO;
import com.vfin.theconnect.service.mapper.FriendMapper;
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

import com.vfin.theconnect.domain.enumeration.FriendType;
/**
 * Integration tests for the {@link FriendResource} REST controller.
 */
@SpringBootTest(classes = TheconnectApp.class)
public class FriendResourceIT {

    private static final Instant DEFAULT_DATE_IS_FRIEND = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_IS_FRIEND = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final FriendType DEFAULT_TYPE = FriendType.REQUEST;
    private static final FriendType UPDATED_TYPE = FriendType.FRIEND;

    @Autowired
    private FriendRepository friendRepository;

    @Autowired
    private FriendMapper friendMapper;

    @Autowired
    private FriendService friendService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restFriendMockMvc;

    private Friend friend;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FriendResource friendResource = new FriendResource(friendService);
        this.restFriendMockMvc = MockMvcBuilders.standaloneSetup(friendResource)
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
    public static Friend createEntity() {
        Friend friend = new Friend()
            .dateIsFriend(DEFAULT_DATE_IS_FRIEND)
            .type(DEFAULT_TYPE);
        return friend;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Friend createUpdatedEntity() {
        Friend friend = new Friend()
            .dateIsFriend(UPDATED_DATE_IS_FRIEND)
            .type(UPDATED_TYPE);
        return friend;
    }

    @BeforeEach
    public void initTest() {
        friendRepository.deleteAll();
        friend = createEntity();
    }

    @Test
    public void createFriend() throws Exception {
        int databaseSizeBeforeCreate = friendRepository.findAll().size();

        // Create the Friend
        FriendDTO friendDTO = friendMapper.toDto(friend);
        restFriendMockMvc.perform(post("/api/friends")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(friendDTO)))
            .andExpect(status().isCreated());

        // Validate the Friend in the database
        List<Friend> friendList = friendRepository.findAll();
        assertThat(friendList).hasSize(databaseSizeBeforeCreate + 1);
        Friend testFriend = friendList.get(friendList.size() - 1);
        assertThat(testFriend.getDateIsFriend()).isEqualTo(DEFAULT_DATE_IS_FRIEND);
        assertThat(testFriend.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    public void createFriendWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = friendRepository.findAll().size();

        // Create the Friend with an existing ID
        friend.setId("existing_id");
        FriendDTO friendDTO = friendMapper.toDto(friend);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFriendMockMvc.perform(post("/api/friends")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(friendDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Friend in the database
        List<Friend> friendList = friendRepository.findAll();
        assertThat(friendList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = friendRepository.findAll().size();
        // set the field null
        friend.setType(null);

        // Create the Friend, which fails.
        FriendDTO friendDTO = friendMapper.toDto(friend);

        restFriendMockMvc.perform(post("/api/friends")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(friendDTO)))
            .andExpect(status().isBadRequest());

        List<Friend> friendList = friendRepository.findAll();
        assertThat(friendList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllFriends() throws Exception {
        // Initialize the database
        friendRepository.save(friend);

        // Get all the friendList
        restFriendMockMvc.perform(get("/api/friends?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(friend.getId())))
            .andExpect(jsonPath("$.[*].dateIsFriend").value(hasItem(DEFAULT_DATE_IS_FRIEND.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }
    
    @Test
    public void getFriend() throws Exception {
        // Initialize the database
        friendRepository.save(friend);

        // Get the friend
        restFriendMockMvc.perform(get("/api/friends/{id}", friend.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(friend.getId()))
            .andExpect(jsonPath("$.dateIsFriend").value(DEFAULT_DATE_IS_FRIEND.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    public void getNonExistingFriend() throws Exception {
        // Get the friend
        restFriendMockMvc.perform(get("/api/friends/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateFriend() throws Exception {
        // Initialize the database
        friendRepository.save(friend);

        int databaseSizeBeforeUpdate = friendRepository.findAll().size();

        // Update the friend
        Friend updatedFriend = friendRepository.findById(friend.getId()).get();
        updatedFriend
            .dateIsFriend(UPDATED_DATE_IS_FRIEND)
            .type(UPDATED_TYPE);
        FriendDTO friendDTO = friendMapper.toDto(updatedFriend);

        restFriendMockMvc.perform(put("/api/friends")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(friendDTO)))
            .andExpect(status().isOk());

        // Validate the Friend in the database
        List<Friend> friendList = friendRepository.findAll();
        assertThat(friendList).hasSize(databaseSizeBeforeUpdate);
        Friend testFriend = friendList.get(friendList.size() - 1);
        assertThat(testFriend.getDateIsFriend()).isEqualTo(UPDATED_DATE_IS_FRIEND);
        assertThat(testFriend.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    public void updateNonExistingFriend() throws Exception {
        int databaseSizeBeforeUpdate = friendRepository.findAll().size();

        // Create the Friend
        FriendDTO friendDTO = friendMapper.toDto(friend);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFriendMockMvc.perform(put("/api/friends")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(friendDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Friend in the database
        List<Friend> friendList = friendRepository.findAll();
        assertThat(friendList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteFriend() throws Exception {
        // Initialize the database
        friendRepository.save(friend);

        int databaseSizeBeforeDelete = friendRepository.findAll().size();

        // Delete the friend
        restFriendMockMvc.perform(delete("/api/friends/{id}", friend.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Friend> friendList = friendRepository.findAll();
        assertThat(friendList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Friend.class);
        Friend friend1 = new Friend();
        friend1.setId("id1");
        Friend friend2 = new Friend();
        friend2.setId(friend1.getId());
        assertThat(friend1).isEqualTo(friend2);
        friend2.setId("id2");
        assertThat(friend1).isNotEqualTo(friend2);
        friend1.setId(null);
        assertThat(friend1).isNotEqualTo(friend2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FriendDTO.class);
        FriendDTO friendDTO1 = new FriendDTO();
        friendDTO1.setId("id1");
        FriendDTO friendDTO2 = new FriendDTO();
        assertThat(friendDTO1).isNotEqualTo(friendDTO2);
        friendDTO2.setId(friendDTO1.getId());
        assertThat(friendDTO1).isEqualTo(friendDTO2);
        friendDTO2.setId("id2");
        assertThat(friendDTO1).isNotEqualTo(friendDTO2);
        friendDTO1.setId(null);
        assertThat(friendDTO1).isNotEqualTo(friendDTO2);
    }
}
