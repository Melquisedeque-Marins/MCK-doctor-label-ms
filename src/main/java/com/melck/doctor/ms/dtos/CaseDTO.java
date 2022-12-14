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
public class CaseDTO {

    @NotBlank
    private String caseDescription;

    public CaseDTO (Case aCase) {
        caseDescription = aCase.getCaseDescription();
    }

}
