package com.example.masterplanbbe.presentation.response;

import com.example.masterplanbbe.domain.entity.Spec;
import com.example.masterplanbbe.domain.enums.SpecCategory;
import com.example.masterplanbbe.domain.enums.CertificationType;

public record CreateSpecResponse(
        Long specId,
        String name,
        String issuingOrganization,
        SpecCategory specCategory,
        CertificationType certificationType,
        String preparation,
        String eligibility,
        String examStructure,
        String passingCriteria
) {
    public CreateSpecResponse(Spec spec){
        this(
                spec.getId(),
                spec.getName(),
                spec.getIssuingOrganization(),
                spec.getSpecCategory(),
                spec.getCertificationType(),
                spec.getExamDetails().get(0).getPreparation(),
                spec.getExamDetails().get(0).getEligibility(),
                spec.getExamDetails().get(0).getExamStructure(),
                spec.getExamDetails().get(0).getPassingCriteria()
        );
    }
}
