package com.example.masterplanbbe.domain.exam.response;

import com.example.masterplanbbe.domain.exam.dto.ExamWithDetailsDto;

public record ReadExamResponse(
        String title,
        String category,
        String authority,
        Double difficulty,
        Integer participantCount,
        String certificationType,
        String preparation,
        String eligibility,
        String examStructure,
        String passingCriteria
) {
    public ReadExamResponse(ExamWithDetailsDto dto) {
        this(
                dto.title(),
                dto.category(),
                dto.authority(),
                dto.difficulty(),
                dto.participantCount(),
                dto.certificationType(),
                dto.preparation(),
                dto.eligibility(),
                dto.examStructure(),
                dto.passingCriteria()
        );
    }
}
