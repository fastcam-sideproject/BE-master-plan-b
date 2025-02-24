package com.example.masterplanbbe.domain.spec.request;

import com.example.masterplanbbe.domain.exam.enums.Category;
import com.example.masterplanbbe.domain.exam.enums.CertificationType;

public record SpecUpdateRequest(
        String name,
        Category category,
        CertificationType certificationType,
        String issuingOrganization
) {
}
