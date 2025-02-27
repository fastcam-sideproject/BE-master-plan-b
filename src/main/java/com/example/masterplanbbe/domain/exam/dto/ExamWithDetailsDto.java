package com.example.masterplanbbe.domain.exam.dto;

import com.example.masterplanbbe.domain.exam.entity.Exam;
import com.example.masterplanbbe.domain.exam.enums.Category;
import com.example.masterplanbbe.domain.exam.enums.CertificationType;
import com.example.masterplanbbe.domain.spec.entity.Spec;
import com.querydsl.core.annotations.QueryProjection;

public record ExamWithDetailsDto(
        String name,
        CertificationType certificationType,
        String issuingOrganization,
        Double difficulty,
        String preparation,
        String eligibility,
        String examStructure,
        String passingCriteria
) {
    @QueryProjection
    public ExamWithDetailsDto(Exam exam,
                              Spec spec) {
        this(
                exam.getName(),
                spec.getCertificationType(),
                spec.getIssuingOrganization(),
                spec.getDifficulty(),
                exam.getExamDetail().getPreparation(),
                exam.getExamDetail().getEligibility(),
                exam.getExamDetail().getExamStructure(),
                exam.getExamDetail().getPassingCriteria()
        );
    }
}
