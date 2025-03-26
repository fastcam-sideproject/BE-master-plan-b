package com.example.masterplanbbe.presentation.response;

import com.example.masterplanbbe.application.dto.RecommendationSpecDTO;
import com.example.masterplanbbe.domain.enums.CertificationType;

import java.time.LocalDate;

public record RecommendationResponseDTO(
        Long specId,
        String specName,
        CertificationType certificationType,
        LocalDate applyStartDate,
        LocalDate applyEndDate,
        LocalDate examStartDate
) {
    public static RecommendationResponseDTO from(RecommendationSpecDTO dto) {
        return new RecommendationResponseDTO(
                dto.specId(), dto.specName(), dto.certificationType(),
                dto.applyStartDate(), dto.applyEndDate(), dto.examStartDate()
        );
    }
}
