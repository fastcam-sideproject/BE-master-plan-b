package com.example.masterplanbbe.domain.spec.response;

import com.example.masterplanbbe.domain.exam.entity.ExamDetail;
import com.example.masterplanbbe.domain.spec.entity.Spec;
import com.example.masterplanbbe.domain.exam.enums.Category;
import com.example.masterplanbbe.domain.exam.enums.CertificationType;

public record CreateSpecResponse(
        Long specId,
        String name,
        String issuingOrganization,
        Category category,
        CertificationType certificationType,
        String preparation,
        String eligibility,
        String examStructure,
        String passingCriteria
) {
    public CreateSpecResponse(Spec spec, ExamDetail examDetail){
        this(
                spec.getId(),
                spec.getName(),
                spec.getIssuingOrganization(),
                spec.getCategory(),
                spec.getCertificationType(),
                examDetail.getPreparation(),
                examDetail.getEligibility(),
                examDetail.getExamStructure(),
                examDetail.getPassingCriteria()
        );
    }
}
