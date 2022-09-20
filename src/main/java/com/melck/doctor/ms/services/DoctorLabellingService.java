package com.melck.doctor.ms.services;

import com.melck.doctor.ms.entities.Case;
import com.melck.doctor.ms.entities.DoctorLabelling;
import com.melck.doctor.ms.repositories.CaseRepository;
import com.melck.doctor.ms.repositories.DoctorLabellingRepository;
import com.melck.doctor.ms.services.exceptions.IntegrityViolation;
import com.melck.doctor.ms.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DoctorLabellingService {

    @Autowired
    private DoctorLabellingRepository repository;

}
