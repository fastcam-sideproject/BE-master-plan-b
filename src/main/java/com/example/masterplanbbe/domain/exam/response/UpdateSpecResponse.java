package com.example.masterplanbbe.domain.exam.response;

import com.example.masterplanbbe.domain.exam.enums.Category;
import com.example.masterplanbbe.domain.exam.enums.CertificationType;

public record UpdateSpecResponse(
        Long specId,
        String name,
        String issuingOrganization,
        Category category,
        CertificationType certificationType
) {
}
