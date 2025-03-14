package com.example.masterplanbbe.presentation.response;

import com.example.masterplanbbe.domain.enums.SpecCategory;
import com.example.masterplanbbe.domain.enums.CertificationType;
import com.example.masterplanbbe.domain.entity.Spec;

public record UpdateSpecResponse(
        Long specId,
        String name,
        String issuingOrganization,
        SpecCategory specCategory,
        CertificationType certificationType,
        Integer participantCount
) {
    public UpdateSpecResponse(Spec spec) {
        this(
                spec.getId(),
                spec.getName(),
                spec.getIssuingOrganization(),
                spec.getSpecCategory(),
                spec.getCertificationType(),
                spec.getParticipantCount()
        );
    }
}
