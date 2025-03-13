package com.example.masterplanbbe.domain.spec.response;

import com.example.masterplanbbe.domain.exam.enums.CertificationType;
import com.example.masterplanbbe.domain.spec.dto.SpecWithDetailsDto;

public record ReadSpecResponse(
        String name,
        String issuingOrganization,
        CertificationType certificationType,
        Boolean isBookmarked,
        String preparation,
        String eligibility,
        String examStructure,
        String passingCriteria
) {
    public ReadSpecResponse(SpecWithDetailsDto dto) {
        this(
                dto.name(),
                dto.issuingOrganization(),
                dto.certificationType(),
                dto.isBookmarked(),
                dto.preparation(),
                dto.eligibility(),
                dto.examStructure(),
                dto.passingCriteria()
        );
    }
}
