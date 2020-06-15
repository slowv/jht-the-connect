package com.vfin.theconnect.web.controller;

import com.vfin.theconnect.service.dto.ImageDTO;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.time.Instant;

@Controller
@RequestMapping(value = "/image")
public class ImageController {

//    @InitBinder("imageDTO")
//    public void initBinder(WebDataBinder binder) {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy~MM~dd");
//        dateFormat.setLenient(false);
//        binder.registerCustomEditor(Instant.class, "createdAt", new CustomDateEditor(dateFormat, false));
//    }

    @PostMapping("/add")
    @ResponseBody
    public ImageDTO store(@Valid @RequestBody ImageDTO imageDTO) {
        return imageDTO;
    }

}
