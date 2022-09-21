package com.melck.doctor.ms.dtos;

import com.melck.doctor.ms.entities.Label;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DoctorLabellingDTO {


    private Long caseId;
    private Long doctorId;
    private Label label;
}
