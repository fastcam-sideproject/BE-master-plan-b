package com.example.masterplanbbe.presentation.response;

import com.example.masterplanbbe.application.dto.ExamWithDetailsDto;
import com.example.masterplanbbe.domain.enums.CertificationType;

public record ReadExamResponse(
    String name,
    String issuingOrganization,
    CertificationType certificationType,
    Boolean isBookmarked,
    String preparation,
    String eligibility,
    String examStructure,
    String passingCriteria
) {
    public ReadExamResponse(ExamWithDetailsDto dto) {
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
