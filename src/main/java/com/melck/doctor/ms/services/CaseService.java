package com.melck.doctor.ms.services;

import com.melck.doctor.ms.dtos.CaseDTO;
import com.melck.doctor.ms.entities.Case;
import com.melck.doctor.ms.entities.Label;
import com.melck.doctor.ms.repositories.CaseRepository;
import com.melck.doctor.ms.services.exceptions.IntegrityViolation;
import com.melck.doctor.ms.services.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CaseService {


    private final CaseRepository repository;
    private final LabelService labelService;

    @Transactional
    public CaseDTO insert(Case c) {
        Case newCase = repository.save(c);
        return new CaseDTO(newCase);
    }

    @Transactional(readOnly = true)
    public Case findById(Long caseId) {
        return repository.findById(caseId)
                .orElseThrow(() -> new ResourceNotFoundException("Case with id: " + caseId + " must be founded"));
    }


    @Transactional(readOnly = true)
    public List<Case> findAll() {
        return repository.findAll();
    }

    public void delete(Long caseId) {
        Case c = findById(caseId);
        try {
            repository.delete(c);
        } catch (DataIntegrityViolationException e) {
            throw new IntegrityViolation("the entity with id: " + c.getCaseId() + " cannot be deleted");
        }
    }

    @Transactional
    public Case update(Long caseId, Case aCase) {
        Case actualCase = findById(caseId);
        actualCase.setCaseDescription(aCase.getCaseDescription());
        return repository.save(actualCase);
    }

    @Transactional
    public Case updateLabel(Long caseId, Label label) {
        Case actualCase = findById(caseId);
        Label updatedLabel = labelService.update(actualCase.getLabel().getLabelId(), label);
        actualCase.setLabel(updatedLabel);
        return repository.save(actualCase);
    }
}
