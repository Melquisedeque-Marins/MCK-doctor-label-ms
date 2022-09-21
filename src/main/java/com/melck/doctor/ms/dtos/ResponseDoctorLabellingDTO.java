package com.melck.doctor.ms.dtos;

import com.melck.doctor.ms.entities.Label;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDoctorLabellingDTO {
    private Long id;
    private Long caseId;
    private String caseDescription;
    private Long doctorId;
    private Label label;
    private Instant createdAt;
}
