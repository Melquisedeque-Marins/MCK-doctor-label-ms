package com.melck.doctor.ms.services;

import com.melck.doctor.ms.dtos.CaseDTO;
import com.melck.doctor.ms.dtos.ResponseCaseDTO;
import com.melck.doctor.ms.entities.Case;
import com.melck.doctor.ms.repositories.CaseRepository;
import com.melck.doctor.ms.services.exceptions.ResourceNotFoundException;
import com.melck.doctor.ms.utils.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
class CaseServiceTest {

    @InjectMocks
    private CaseService service;

    @Mock
    private CaseRepository repository;

    private long existingId;
    private long nonExistingId;
    private long dependentId;
    private Case aCase;
    private Case aCaseInvalid;
    private CaseDTO caseDTO;
    private CaseDTO caseDTOInvalid;
    private ResponseCaseDTO respDTO;
    private PageImpl<Case> page;

    @BeforeEach
    void setUp() throws Exception {
        existingId = 1L;
        nonExistingId = 2L;
        dependentId = 3L;
        aCase = Factory.createCase();
        aCaseInvalid = Factory.createCase();
        aCaseInvalid.setCaseDescription("");
        caseDTO = Factory.createCaseDTO();
        caseDTOInvalid = Factory.createCaseDTO();
        caseDTOInvalid.setCaseDescription("");
        respDTO = Factory.createResponseCaseDTO();

        page = new PageImpl<>(List.of(aCase));

        Mockito.when(repository.findAll((Pageable)any())).thenReturn(page);

        Mockito.when(repository.save(any())).thenReturn(aCase);
       // Mockito.when(repository.save(aCaseInvalid)).thenThrow(MethodArgumentNotValidException.class);

        Mockito.when(repository.findById(existingId)).thenReturn(Optional.of(aCase));
        Mockito.when(repository.findById(nonExistingId)).thenReturn(Optional.empty());

        Mockito.when(repository.findAllPaged(any(), any())).thenReturn(page);

        Mockito.doNothing().when(repository).deleteById(existingId);
        Mockito.doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(nonExistingId);
        Mockito.doThrow(DataIntegrityViolationException.class).when(repository).deleteById(dependentId);
    }

    @Test
    public void insertShouldReturnAResponseCaseDtoWhenTheInputDataIsValid() {
        ResponseCaseDTO result = service.insert(caseDTO);
        Assertions.assertFalse(result == null);
        Assertions.assertEquals(caseDTO.getCaseDescription(), result.getCaseDescription());
        Mockito.verify(repository, times(1)).save(new Case());
    }

    @Test
    public void findByIdShouldReturnACaseObjectWhenIdExist(){
        Case result = service.findById(existingId);
        Assertions.assertNotNull(result);
        Mockito.verify(repository, times(1)).findById(existingId);
    }

    @Test
    public void findByIdShouldThrowsResourceNotFoundExceptionWhenIdDoesNotExists(){
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.findById(nonExistingId);
        });
    }

    @Test
    public void findAllCasesShouldReturnAPageOfCases(){
        Page<ResponseCaseDTO> result = service.findAllCases(any());
        Assertions.assertTrue(result != null);
    }

    @Test
    public void updateShouldReturnAnUpdatedCaseWhenIdExists(){
        Case result = service.update(existingId, aCase);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getCaseDescription(), aCase.getCaseDescription());
    }

    @Test
    public void updateShouldThrowsResourceNotFoundExceptionWhenIdDoesNotExists(){
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.update(nonExistingId, aCase);
        });
    }
}
//    @Test
//    public void insertShouldThrowsMethodArgumentNotValidExceptionWhenTheInputDataDoesNotValid() {
//        Assertions.assertThrows(MethodArgumentNotValidException.class, () -> {
//            service.insert(caseDTOInvalid);
//        });
//    }
