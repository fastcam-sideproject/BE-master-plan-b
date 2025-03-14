package com.example.masterplanbbe.domain.exam.dto;

import com.example.masterplanbbe.domain.exam.enums.CertificationType;
import com.querydsl.core.annotations.QueryProjection;


public @QueryProjection record ExamWithDetailsDto(
        String name,
        String issuingOrganization,
        CertificationType certificationType,
        Boolean isBookmarked,
        String preparation,
        String eligibility,
        String examStructure,
        String passingCriteria
) {
}
