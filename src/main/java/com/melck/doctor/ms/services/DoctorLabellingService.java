package com.melck.doctor.ms.services;

import com.melck.doctor.ms.dtos.DoctorLabellingDTO;
import com.melck.doctor.ms.dtos.ResponseDoctorLabellingDTO;
import com.melck.doctor.ms.entities.Case;
import com.melck.doctor.ms.entities.DoctorLabelling;
import com.melck.doctor.ms.entities.Label;
import com.melck.doctor.ms.repositories.DoctorLabellingRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class DoctorLabellingService {

    private final DoctorLabellingRepository repository;
    private final LabelService labelService;
    private final CaseService caseService;

    private static Logger logger = LoggerFactory.getLogger(CaseService.class);

    @Transactional
    public ResponseDoctorLabellingDTO insert(DoctorLabellingDTO dto) {
        DoctorLabelling dl = convertToDoctor(dto);
        Label labelToSave = new Label(null, dto.getCode(), dto.getDescription());
        var newLabel = labelService.insert(labelToSave);
        dl.getACase().setLabel(newLabel);
        var newDl = repository.save(dl);
        logger.info("The labelling was created with success");
        return convertToResponseDto(newDl);
    }

    @Transactional(readOnly = true)
    public ResponseDoctorLabellingDTO findById(Long id) {
       // DoctorLabelling doctor = repository.findById(id);
        return null;
    }

    private DoctorLabelling convertToDoctor(DoctorLabellingDTO dto){
        DoctorLabelling doctorLabelling = new DoctorLabelling();
        Case aCase = caseService.findById(dto.getCaseId());
        doctorLabelling.setDoctorId(dto.getDoctorId());
        doctorLabelling.setACase(aCase);
        return doctorLabelling;
    }

    private ResponseDoctorLabellingDTO convertToResponseDto(DoctorLabelling labelling){
        ResponseDoctorLabellingDTO dto = new ResponseDoctorLabellingDTO();
        dto.setId(labelling.getId());
        dto.setCaseId(labelling.getACase().getCaseId());
        dto.setCaseDescription(labelling.getACase().getCaseDescription());
        dto.setDoctorId(labelling.getDoctorId());
        dto.setLabel(labelling.getACase().getLabel());
        dto.setCreatedAt(labelling.getCreatedAt());
        return dto;
    }

}
