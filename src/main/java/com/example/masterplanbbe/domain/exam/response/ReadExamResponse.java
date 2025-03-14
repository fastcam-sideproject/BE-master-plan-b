package com.example.masterplanbbe.domain.exam.response;

import com.example.masterplanbbe.domain.exam.dto.ExamWithDetailsDto;
import com.example.masterplanbbe.domain.exam.enums.Category;
import com.example.masterplanbbe.domain.exam.enums.CertificationType;

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
