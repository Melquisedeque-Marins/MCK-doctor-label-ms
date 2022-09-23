package com.melck.doctor.ms.utils;

import com.melck.doctor.ms.dtos.CaseDTO;
import com.melck.doctor.ms.dtos.ResponseCaseDTO;
import com.melck.doctor.ms.entities.Case;

import java.time.Instant;

public class Factory {

    public static Case createCase() {
        Case c = new Case(1L, "description", null, Instant.now(), null);
        return c;
    }

    public static CaseDTO createCaseDTO() {
        Case c = createCase();
        return new CaseDTO(c);
    }

    public static ResponseCaseDTO createResponseCaseDTO(){
        Case c = createCase();
        return new ResponseCaseDTO(c);
    }
}
