package com.example.masterplanbbe.domain.spec.request;

import com.example.masterplanbbe.domain.exam.enums.Category;
import com.example.masterplanbbe.domain.exam.enums.CertificationType;
import com.example.masterplanbbe.domain.spec.entity.Spec;


public record SpecUpdateRequest(
        String name,
        Category category,
        CertificationType certificationType,
        String issuingOrganization,
        Integer participantCount
) {
    public void update(Spec spec) {
        spec.update(name, issuingOrganization, category, certificationType, participantCount);
    }
}
