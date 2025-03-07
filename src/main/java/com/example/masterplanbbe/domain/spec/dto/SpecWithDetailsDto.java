package com.example.masterplanbbe.domain.spec.dto;

import com.example.masterplanbbe.domain.exam.entity.ExamDetail;
import com.example.masterplanbbe.domain.exam.enums.CertificationType;
import com.example.masterplanbbe.domain.spec.entity.Spec;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class SpecWithDetailsDto {
    private final String name;
    private final String issuingOrganization;
    private final CertificationType certificationType;
    private final Boolean isBookmarked;
    private final String preparation;
    private final String eligibility;
    private final String examStructure;
    private final String passingCriteria;

    @QueryProjection
    public SpecWithDetailsDto(
            String name,
            String issuingOrganization,
            CertificationType certificationType,
            Boolean isBookmarked,
            String preparation,
            String eligibility,
            String examStructure,
            String passingCriteria
    ) {
        this.name = name;
        this.issuingOrganization = issuingOrganization;
        this.certificationType = certificationType;
        this.isBookmarked = isBookmarked;
        this.preparation = preparation;
        this.eligibility = eligibility;
        this.examStructure = examStructure;
        this.passingCriteria = passingCriteria;
    }
}
