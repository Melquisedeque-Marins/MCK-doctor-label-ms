package com.melck.doctor.ms.services.exceptions;

public class IntegrityViolation extends RuntimeException{
    public IntegrityViolation(String message) {
        super(message);
    }
}
