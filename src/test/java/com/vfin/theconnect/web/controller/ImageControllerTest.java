package com.vfin.theconnect.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vfin.theconnect.TheconnectApp;
import com.vfin.theconnect.domain.Image;
import com.vfin.theconnect.service.dto.ImageDTO;
import com.vfin.theconnect.service.mapper.ImageMapper;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.StringReader;
import java.time.Instant;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = TheconnectApp.class)
public class ImageControllerTest {
    private MockMvc mvc = MockMvcBuilders.standaloneSetup(new ImageController()).build();
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ImageMapper imageMapper;

    @Test
    public void itShouldAddImageSuccess() throws Exception {
        Image image = new Image();
        image.setId("1");
        image.url("http://www.thymeleaf.org")
            .status(1)
            .createdAt(Instant.now())
            .updatedAt(Instant.now());

        MvcResult result = mvc.perform(post("/image/add")
            .content(objectMapper.writeValueAsString(imageMapper.toDto(image)))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();

        Image imgResult = imageMapper
            .toEntity(objectMapper.readValue(new StringReader(result.getResponse().getContentAsString()), ImageDTO.class));
        System.out.println(imgResult.toString());
        assertThat(imgResult.getUrl()).isEqualTo(image.getUrl());
    }

    @Test
    public void itShouldAddFail_By_UrlIsNull_And_ReturnMessageErrors() throws Exception {
        Image image = new Image();
        image.setId("1");
        image.url(null)
            .status(1)
            .createdAt(Instant.now())
            .updatedAt(Instant.now());

        mvc.perform(post("/image/add")
            .content(objectMapper.writeValueAsString(imageMapper.toDto(image)))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andReturn();
    }
}
