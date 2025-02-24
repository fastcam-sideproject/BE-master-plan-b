package com.example.masterplanbbe.domain.exam.response;

import com.example.masterplanbbe.domain.exam.enums.Category;

public record ReadSpecResponse(
        String name,
        Category category,
        String issuingOrganization,
        Double difficulty,
        Integer participantCount,
        String preparation,
        String eligibility,
        String examStructure,
        String passingCriteria
) {
    public ReadSpecResponse(SpecWithDetailsDto dto) {
        this(
                dto.name(),
                dto.category(),
                dto.issuingOrganization(),
                dto.difficulty(),
                dto.participantCount(),
                dto.preparation(),
                dto.eligibility(),
                dto.examStructure(),
                dto.passingCriteria()
        );
    }
}
