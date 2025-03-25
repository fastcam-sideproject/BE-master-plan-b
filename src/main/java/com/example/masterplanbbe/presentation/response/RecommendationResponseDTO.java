package com.example.masterplanbbe.presentation.response;

import com.example.masterplanbbe.domain.entity.Spec;
import com.example.masterplanbbe.domain.enums.CertificationType;

import java.time.LocalDate;

public record RecommendationResponseDTO(
        Long specId,
        String specName,
        CertificationType certificationType,
        LocalDate applyEndDate,
        LocalDate examStartDate
) {
    public static RecommendationResponseDTO from(Spec spec) {
        return new RecommendationResponseDTO(
                spec.getId(),
                spec.getName(),
                spec.getCertificationType(),
                spec.getLatestExam().getApplyEndDate(),
                spec.getLatestExam().getExamStartDate()
        );
    }
}
