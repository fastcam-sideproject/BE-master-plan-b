package com.example.masterplanbbe.domain.spec.response;

import com.example.masterplanbbe.domain.exam.entity.ExamDetail;
import com.example.masterplanbbe.domain.spec.entity.Spec;
import com.example.masterplanbbe.domain.exam.enums.Category;
import com.example.masterplanbbe.domain.exam.enums.CertificationType;

import java.util.List;

public record CreateSpecResponse(
        Long specId,
        String name,
        String issuingOrganization,
        Category category,
        CertificationType certificationType,
        ExamDetail examDetail
) {
    public CreateSpecResponse(Spec spec){
        this(
                spec.getId(),
                spec.getName(),
                spec.getIssuingOrganization(),
                spec.getCategory(),
                spec.getCertificationType(),
                spec.getExamDetails().get(0)
        );
    }
}
