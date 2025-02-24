package com.example.masterplanbbe.domain.spec.response;

import com.example.masterplanbbe.domain.exam.enums.Category;
import com.example.masterplanbbe.domain.exam.enums.CertificationType;

public record ReadSpecResponse(
        String name,
        String issuingOrganization,
        Category category,
        CertificationType certificationType,
        Double difficulty,
        Integer participantCount,
        String preparation,
        String eligibility,
        String examStructure,
        String passingCriteria
) {
    public ReadSpecResponse(SpecWithDetailsDto dto) {
        this(
                dto.getName(),
                dto.getIssuingOrganization(),
                dto.getCategory(),
                dto.getCertificationType(),
                dto.getDifficulty(),
                dto.getParticipantCount(),
                dto.getPreparation(),
                dto.getEligibility(),
                dto.getExamStructure(),
                dto.getPassingCriteria()
        );
    }
}
