package com.melck.doctor.ms.dtos;

import com.melck.doctor.ms.entities.Label;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DoctorLabellingDTO {

    @NotBlank
    private Long caseId;

    @NotBlank
    private Long doctorId;

    @NotBlank
    private Label label;
}
