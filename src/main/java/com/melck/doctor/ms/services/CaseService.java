package com.melck.doctor.ms.services;

import com.melck.doctor.ms.entities.Case;
import com.melck.doctor.ms.repositories.CaseRepository;
import com.melck.doctor.ms.services.exceptions.IntegrityViolation;
import com.melck.doctor.ms.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CaseService {

    @Autowired
    private CaseRepository repository;

    @Transactional
    public Case insert(Case c) {
        Case newCase = repository.save(c);
        return newCase;
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
}
