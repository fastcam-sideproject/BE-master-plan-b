package com.example.masterplanbbe.domain.exam.dto;

import com.example.masterplanbbe.domain.exam.entity.Exam;
import com.querydsl.core.annotations.QueryProjection;

public record ExamWithDetailsDto(
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
    @QueryProjection
    public ExamWithDetailsDto(Exam exam) {
        this(
                exam.getTitle(),
                exam.getCategory().name(),
                exam.getAuthority(),
                exam.getDifficulty(),
                exam.getParticipantCount(),
                exam.getCertificationType().name(),
                exam.getExamDetail().getPreparation(),
                exam.getExamDetail().getEligibility(),
                exam.getExamDetail().getExamStructure(),
                exam.getExamDetail().getPassingCriteria()
        );
    }
}
