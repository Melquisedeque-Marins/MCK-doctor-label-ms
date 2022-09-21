package com.melck.doctor.ms.services;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.melck.doctor.ms.dtos.DoctorLabellingDTO;
import com.melck.doctor.ms.entities.Case;
import com.melck.doctor.ms.entities.Doctor;
import com.melck.doctor.ms.entities.DoctorLabelling;
import com.melck.doctor.ms.entities.Label;
import com.melck.doctor.ms.repositories.CaseRepository;
import com.melck.doctor.ms.repositories.DoctorLabellingRepository;
import com.melck.doctor.ms.services.exceptions.IntegrityViolation;
import com.melck.doctor.ms.services.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DoctorLabellingService {

    private final DoctorLabellingRepository repository;
    private final LabelService labelService;
    private final CaseService caseService;
    private final DoctorService doctorService;


    @Transactional
    public DoctorLabelling insert(DoctorLabellingDTO dto, Label label) {
        DoctorLabelling dl = convertToDoctor(dto);
        Label newLabel = labelService.insert(label);
        dl.getACase().setLabel(newLabel);
        dl.setLabel(newLabel);
        return repository.save(dl);
    }

    private DoctorLabelling convertToDoctor(DoctorLabellingDTO dto){
        DoctorLabelling doctorLabelling = new DoctorLabelling();
        Case aCase = caseService.findById(dto.getCaseId());
        Doctor doctor = doctorService.findById(dto.getDoctorId());
        doctorLabelling.setDoctor(doctor);
        doctorLabelling.setACase(aCase);
        return doctorLabelling;
    }
}
