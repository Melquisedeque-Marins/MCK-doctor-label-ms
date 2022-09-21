package com.melck.doctor.ms.services;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.melck.doctor.ms.dtos.DoctorLabellingDTO;
import com.melck.doctor.ms.dtos.ResponseDoctorLabellingDTO;
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
    public ResponseDoctorLabellingDTO insert(DoctorLabellingDTO dto) {
        DoctorLabelling dl = convertToDoctor(dto);
        Label newLabel = labelService.insert(dto.getLabel());
        dl.getACase().setLabel(newLabel);
        dl.setLabel(newLabel);
        var newDl = repository.save(dl);
        var respDto = convertToResponseDto(newDl);
        return respDto;
    }

    private DoctorLabelling convertToDoctor(DoctorLabellingDTO dto){
        DoctorLabelling doctorLabelling = new DoctorLabelling();
        Case aCase = caseService.findById(dto.getCaseId());
        Doctor doctor = doctorService.findById(dto.getDoctorId());
        doctorLabelling.setDoctor(doctor);
        doctorLabelling.setACase(aCase);
        return doctorLabelling;
    }

    private ResponseDoctorLabellingDTO convertToResponseDto(DoctorLabelling labelling){
        ResponseDoctorLabellingDTO dto = new ResponseDoctorLabellingDTO();
        dto.setId(labelling.getId());
        dto.setCaseId(labelling.getACase().getCaseId());
        dto.setCaseDescription(labelling.getACase().getCaseDescription());
        dto.setDoctorId(labelling.getDoctor().getDoctorId());
        dto.setLabel(labelling.getLabel());
        dto.setCreatedAt(labelling.getCreatedAt());
        return dto;
    }
}
