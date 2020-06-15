package com.vfin.theconnect.web.controller;

import com.vfin.theconnect.service.dto.CountryDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/country")
public class CountryController {

    @PostMapping("/add")
    @ResponseBody
    public CountryDTO store(@Valid @RequestBody CountryDTO countryDTO) {
        return countryDTO;
    }


}
