package com.example.masterplanbbe.domain.spec.response;

import com.example.masterplanbbe.domain.exam.enums.CertificationType;
import com.example.masterplanbbe.domain.spec.dto.SpecWithDetailsDto;

public record ReadSpecResponse(
        String name,
        String issuingOrganization,
        CertificationType certificationType,
        String preparation,
        String eligibility,
        String examStructure,
        String passingCriteria
) {
    public ReadSpecResponse(SpecWithDetailsDto dto) {
        this(
                dto.getName(),
                dto.getIssuingOrganization(),
                dto.getCertificationType(),
                dto.getPreparation(),
                dto.getEligibility(),
                dto.getExamStructure(),
                dto.getPassingCriteria()
        );
    }
}
