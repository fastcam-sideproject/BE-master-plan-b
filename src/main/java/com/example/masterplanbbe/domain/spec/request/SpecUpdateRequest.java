package com.example.masterplanbbe.domain.spec.request;

import com.example.masterplanbbe.domain.exam.entity.ExamDetail;
import com.example.masterplanbbe.domain.exam.enums.Category;
import com.example.masterplanbbe.domain.exam.enums.CertificationType;
import com.example.masterplanbbe.domain.spec.entity.Spec;

import java.util.List;

public record SpecUpdateRequest(
        String name,
        Category category,
        CertificationType certificationType,
        String issuingOrganization,
        Double difficulty,
        Integer participantCount,
        List<ExamDetail> examDetails
) {
    public void update(Spec spec) {
        spec.update(name, issuingOrganization, category, certificationType, difficulty, participantCount,examDetails);
    }
}
