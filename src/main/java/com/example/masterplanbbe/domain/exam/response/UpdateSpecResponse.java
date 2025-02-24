package com.example.masterplanbbe.domain.exam.response;

import com.example.masterplanbbe.domain.exam.enums.Category;
import com.example.masterplanbbe.domain.exam.enums.CertificationType;

public record UpdateSpecResponse(
        Long specId,
        String name,
        Category category,
        CertificationType certificationType,
        String issuingOrganization
) {
    public UpdateSpecResponse(Long specId, String name, Category category, CertificationType certificationType, String issuingOrganization) {
        this.specId = specId;
        this.name = name;
        this.category = category;
        this.certificationType = certificationType;
        this.issuingOrganization = issuingOrganization;
    }
}
