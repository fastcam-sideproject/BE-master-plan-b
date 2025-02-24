package com.example.masterplanbbe.domain.exam.request;

import com.example.masterplanbbe.domain.exam.entity.ExamDetail;
import com.example.masterplanbbe.domain.exam.entity.Spec;
import com.example.masterplanbbe.domain.exam.enums.Category;
import com.example.masterplanbbe.domain.exam.enums.CertificationType;
import org.springframework.data.util.Pair;

public record SpecCreateRequest(
        String name,
        Category category,
        CertificationType certificationType,
        String issuingOrganization,
        String preparation,
        String eligibility,
        String examStructure,
        String passingCriteria
) {
    public Spec toSpec() {
        return new Spec(
                name,
                category,
                certificationType,
                issuingOrganization
        );
    }

    public ExamDetail toExamDetail(Spec spec) {
        return new ExamDetail(
                spec,
                preparation,
                eligibility,
                examStructure,
                passingCriteria
        );
    }
}
