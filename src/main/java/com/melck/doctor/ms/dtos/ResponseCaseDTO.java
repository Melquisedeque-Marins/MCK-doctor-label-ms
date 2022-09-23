package com.melck.doctor.ms.dtos;

import com.melck.doctor.ms.entities.Case;
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
public class ResponseCaseDTO {


    private Long caseId;
    @NotBlank
    private String caseDescription;
    private Label label;
    private Instant createdAt;

    public ResponseCaseDTO(Case aCase) {
        this.caseId = aCase.getCaseId();
        caseDescription = aCase.getCaseDescription();
        createdAt = aCase.getCreatedAt();
        label = aCase.getLabel();

    }


}
