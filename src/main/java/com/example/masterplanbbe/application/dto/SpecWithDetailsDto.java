package com.example.masterplanbbe.application.dto;

import com.example.masterplanbbe.domain.enums.CertificationType;
import com.querydsl.core.annotations.QueryProjection;

public @QueryProjection record SpecWithDetailsDto (
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
