package com.melck.doctor.ms.resources;

import com.melck.doctor.ms.entities.Case;
import com.melck.doctor.ms.entities.DoctorLabelling;
import com.melck.doctor.ms.services.CaseService;
import com.melck.doctor.ms.services.DoctorLabellingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/labellings")
public class DoctorLabellingResource {

    @Autowired
    private DoctorLabellingService service;

}