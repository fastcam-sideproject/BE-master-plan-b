package com.example.masterplanbbe.application.dto;

import com.example.masterplanbbe.domain.enums.CertificationType;
import com.querydsl.core.annotations.QueryProjection;

import java.time.LocalDate;

public @QueryProjection record RecommendationSpecDTO(
        Long specId,
        String specName,
        CertificationType certificationType,
        LocalDate applyStartDate,
        LocalDate applyEndDate,
        LocalDate examStartDate
) {
}
