package com.melck.doctor.ms.dtos;

import com.melck.doctor.ms.entities.Label;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DoctorLabellingDTO {

    @NotNull(message = "The case ID field cannot be null")
    private Long caseId;

    @NotNull(message = "The doctor ID field cannot be null")
    private Long doctorId;

    @NotNull(message = "The label field cannot be null")
    private String code;

    @NotNull(message = "The label field cannot be null")
    private String description;


}
