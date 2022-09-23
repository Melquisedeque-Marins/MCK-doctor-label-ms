package com.melck.doctor.ms.services;

import com.melck.doctor.ms.DoctorLabelMsApplication;
import com.melck.doctor.ms.dtos.CaseDTO;
import com.melck.doctor.ms.dtos.ResponseCaseDTO;
import com.melck.doctor.ms.entities.Case;
import com.melck.doctor.ms.entities.Label;
import com.melck.doctor.ms.repositories.CaseRepository;
import com.melck.doctor.ms.services.exceptions.DataIntegrityViolationException;
import com.melck.doctor.ms.services.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CaseService {


    private final CaseRepository repository;
    private final LabelService labelService;

    private static Logger logger = LoggerFactory.getLogger(CaseService.class);

    @Transactional
    public ResponseCaseDTO insert(CaseDTO dto) {
        Case aCase = new Case();
        BeanUtils.copyProperties(dto, aCase);
        Case newCase = repository.save(aCase);
        logger.info("The case was created with success");
        return new ResponseCaseDTO(newCase);

    }

    @Transactional(readOnly = true)
    public Case findById(Long caseId) {

        try {
            Case c = repository.findById(caseId).get();
            logger.info("the case with id: " + caseId + " was founded.");
            return c;
        } catch (NoSuchElementException e) {
            logger.error("Case with id: " + caseId + " cannot be founded. " + e.getMessage(), e );
            throw new ResourceNotFoundException("Case with id: " + caseId + " cannot be founded.");
        }
//        return repository.findById(caseId)
//                .orElseThrow(() -> new ResourceNotFoundException("Case with id: " + caseId + " cannot be founded"));
    }


    @Transactional(readOnly = true)
    public List<ResponseCaseDTO> findAll() {
        List<Case> cases = repository.findAll();
        return cases.stream().map(c -> new ResponseCaseDTO(c)).collect(Collectors.toList());
    }

    public void delete(Long caseId) {
        Case c = findById(caseId);
        try {
            repository.delete(c);
            logger.info("The case with id: " + caseId + " was deleted with success.");
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            logger.error("The case cannot be deleted as the case with id: " + caseId + " is tied to another object.", e.getMessage(), e);
            throw new DataIntegrityViolationException("the Case with id: " + caseId + " cannot be deleted.");
        }
    }

    @Transactional
    public Case update(Long caseId, Case aCase) {
        Case actualCase = findById(caseId);
        actualCase.setCaseDescription(aCase.getCaseDescription());
        logger.info("This case with id: " + caseId + "was updated with success.");
        return repository.save(actualCase);
    }


    public Case deleteLabel(Long caseId) {
        Case actualCase = findById(caseId);
        try {
            Long labelId = actualCase.getLabel().getLabelId();
            actualCase.setLabel(null);
            repository.save(actualCase);
            labelService.delete(labelId);
            logger.info("The Label in case with id: " + caseId + " was removed with success.");
            return actualCase;
        } catch (NullPointerException e){
            logger.info("does not exist a label in the case with id: " + caseId , e.getMessage(), e);
            throw new ResourceNotFoundException("Case with id: " + caseId + " does not have a label");

        }

    }

    @Transactional
    public Case updateLabel(Long caseId, Label label) {
        Case actualCase = findById(caseId);
        Label updatedLabel = labelService.update(actualCase.getLabel().getLabelId(), label);
        actualCase.setLabel(updatedLabel);
        logger.info("The Label in case with id: " + caseId + "was updated with success.");
        return repository.save(actualCase);
    }

}
