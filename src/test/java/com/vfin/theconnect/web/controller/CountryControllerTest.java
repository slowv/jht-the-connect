package com.vfin.theconnect.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vfin.theconnect.TheconnectApp;
import com.vfin.theconnect.domain.Country;
import com.vfin.theconnect.service.mapper.CountryMapper;
import com.vfin.theconnect.service.mapper.CountryMapperImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// Test model attribute
@SpringBootTest(classes = TheconnectApp.class)
public class CountryControllerTest {
    private MockMvc mvc = MockMvcBuilders.standaloneSetup(new CountryController()).build();

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CountryMapper countryMapper;

    @Test
    public void itShouldAddCountrySuccess() throws Exception {
        Country country = new Country();
        country.setId("1");
        country.setName("Việt Nam");
        mvc.perform(post("/country/add")
            .content(objectMapper.writeValueAsString(countryMapper.toDto(country)))
            .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    public void itShouldAddCountryFail_By_NameIsNull() throws Exception {
        Country country = new Country();
        country.setId("1");
        mvc.perform(post("/country/add")
            .content(objectMapper.writeValueAsString(countryMapper.toDto(country)))
            .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }
}
