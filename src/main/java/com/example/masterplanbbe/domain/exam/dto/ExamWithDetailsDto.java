package com.example.masterplanbbe.domain.exam.dto;

import com.example.masterplanbbe.domain.exam.entity.Exam;
import com.example.masterplanbbe.domain.exam.enums.Category;
import com.example.masterplanbbe.domain.exam.enums.CertificationType;
import com.querydsl.core.annotations.QueryProjection;

public record ExamWithDetailsDto(
        String title,
        Category category,
        String authority,
        Double difficulty,
        Integer participantCount,
        CertificationType certificationType,
        String preparation,
        String eligibility,
        String examStructure,
        String passingCriteria
) {
    @QueryProjection
    public ExamWithDetailsDto(Exam exam) {
        this(
                exam.getTitle(),
                exam.getCategory(),
                exam.getAuthority(),
                exam.getDifficulty(),
                exam.getParticipantCount(),
                exam.getCertificationType(),
                exam.getExamDetail().getPreparation(),
                exam.getExamDetail().getEligibility(),
                exam.getExamDetail().getExamStructure(),
                exam.getExamDetail().getPassingCriteria()
        );
    }
}
