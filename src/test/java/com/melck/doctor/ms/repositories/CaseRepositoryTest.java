package com.melck.doctor.ms.repositories;


import java.util.Optional;

import com.melck.doctor.ms.entities.Case;
import com.melck.doctor.ms.utils.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CaseRepositoryTest {

    @Autowired
    private CaseRepository repository;

    private long existingId;
    private long nonExistingId;
    private long existingIdWithOutLabel;
    private long countTotalProducts;
    private Case aCase;
    private Case invalidCase;
    @BeforeEach
    void setUp() throws Exception {
        existingId = 1L;
        nonExistingId = 1000L;
        countTotalProducts = repository.count();;
        aCase = Factory.createCase();
        aCase.setCaseId(null);
        invalidCase = Factory.createCase();
        invalidCase.setCaseId(null);
        invalidCase.setCaseDescription("");
    }

    @Test
    public void saveShouldPersistWithAutoincrementWhenIdIsNullAndTheInputDataDoesNotValid() {
        aCase = repository.save(aCase);
        Optional<Case> result = repository.findById(aCase.getCaseId());

        Assertions.assertNotNull(aCase.getCaseId());
        Assertions.assertEquals(countTotalProducts + 1L, aCase.getCaseId());
        Assertions.assertTrue(result.isPresent());
        Assertions.assertSame(result.get(), aCase);
    }

//    @Test
//    public void saveShouldThrowsAnMethodArgumentNotValidExceptionWhenTheInputDataDoesNotValid() {
//        Assertions.assertThrows(MethodArgumentNotValidException.class, () -> {
//            repository.save(invalidCase);
//        });
//    }

    @Test
    public void findByIdShouldReturnANonEmptyOptionalWhenIdExists() {
        Optional<Case> result = repository.findById(1L);

        assertEquals(result.get().getCaseId(), existingId);
    }

    @Test
    public void deleteShouldDeleteObjectWhenIdExists() {
        repository.deleteById(existingIdWithOutLabel);
        Optional<Case> result = repository.findById(existingIdWithOutLabel);
        Assertions.assertFalse(result.isPresent());
    }

    @Test
    public void deleteShouldThrowEmptyResultDataAccessExceptionWhenIdDoesNotExist() {

        Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
            repository.deleteById(nonExistingId);
        });
    }




}