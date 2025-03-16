package com.example.masterplanbbe.presentation.request;

import com.example.masterplanbbe.domain.enums.SpecCategory;
import com.example.masterplanbbe.domain.enums.CertificationType;
import com.example.masterplanbbe.domain.entity.Spec;


public record SpecUpdateRequest(
        String name,
        SpecCategory specCategory,
        CertificationType certificationType,
        String issuingOrganization,
        Integer participantCount
) {
    public void update(Spec spec) {
        spec.update(name, issuingOrganization, specCategory, certificationType, participantCount);
    }
}
