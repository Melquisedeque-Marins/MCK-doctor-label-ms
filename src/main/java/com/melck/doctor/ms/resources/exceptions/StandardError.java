package com.melck.doctor.ms.resources.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StandardError {
    private Instant timeStamp;
    private Integer status;
    private String error;
    private String path;
}