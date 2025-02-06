package com.example.masterplanbbe.domain.exam.response;

import com.example.masterplanbbe.domain.exam.dto.ExamWithDetailsDto;
import com.example.masterplanbbe.domain.exam.enums.Category;
import com.example.masterplanbbe.domain.exam.enums.CertificationType;

public record ReadExamResponse(
        String title,
        Category category,
        CertificationType certificationType,
        String authority,
        Double difficulty,
        Integer participantCount,
        String preparation,
        String eligibility,
        String examStructure,
        String passingCriteria
) {
    public ReadExamResponse(ExamWithDetailsDto dto) {
        this(
                dto.title(),
                dto.category(),
                dto.certificationType(),
                dto.authority(),
                dto.difficulty(),
                dto.participantCount(),
                dto.preparation(),
                dto.eligibility(),
                dto.examStructure(),
                dto.passingCriteria()
        );
    }
}
