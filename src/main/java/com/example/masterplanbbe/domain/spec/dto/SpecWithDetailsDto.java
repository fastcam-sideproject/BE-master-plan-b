package com.example.masterplanbbe.domain.spec.dto;

import com.example.masterplanbbe.domain.exam.entity.ExamDetail;
import com.example.masterplanbbe.domain.exam.enums.CertificationType;
import com.example.masterplanbbe.domain.spec.entity.Spec;
import com.querydsl.core.annotations.QueryProjection;

public record SpecWithDetailsDto(
        String name,
        String issuingOrganization,
        CertificationType certificationType,
        Boolean isBookmarked,
        String preparation,
        String eligibility,
        String examStructure,
        String passingCriteria
) {
    @QueryProjection
    public SpecWithDetailsDto(Spec spec, ExamDetail examDetail, Boolean isBookmarked) {
        this(
                spec.getName(),
                spec.getIssuingOrganization(),
                spec.getCertificationType(),
                isBookmarked,
                examDetail.getPreparation(),
                examDetail.getEligibility(),
                examDetail.getExamStructure(),
                examDetail.getPassingCriteria()
        );
    }
}
