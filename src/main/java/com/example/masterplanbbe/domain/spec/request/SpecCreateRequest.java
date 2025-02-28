package com.example.masterplanbbe.domain.spec.request;

import com.example.masterplanbbe.domain.spec.entity.Spec;
import com.example.masterplanbbe.domain.exam.enums.Category;
import com.example.masterplanbbe.domain.exam.enums.CertificationType;

public record SpecCreateRequest(
        String name,
        String issuingOrganization,
        Category category,
        CertificationType certificationType,
        String preparation,
        String eligibility,
        String examStructure,
        String passingCriteria
) {
    public Spec toSpec() {
        return new Spec(
                name,
                issuingOrganization,
                category,
                certificationType,
                0.0,
                0,
                null
        );
    }

}
