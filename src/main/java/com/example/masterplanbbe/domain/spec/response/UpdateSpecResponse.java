package com.example.masterplanbbe.domain.spec.response;

import com.example.masterplanbbe.domain.exam.entity.ExamDetail;
import com.example.masterplanbbe.domain.exam.enums.Category;
import com.example.masterplanbbe.domain.exam.enums.CertificationType;
import com.example.masterplanbbe.domain.exam.response.UpdateExamResponse;
import com.example.masterplanbbe.domain.spec.entity.Spec;

import java.util.List;

public record UpdateSpecResponse(
        Long specId,
        String name,
        String issuingOrganization,
        Category category,
        CertificationType certificationType,
        Double difficulty,
        Integer participantCount
) {
    public UpdateSpecResponse(Spec spec) {
        this(
                spec.getId(),
                spec.getName(),
                spec.getIssuingOrganization(),
                spec.getCategory(),
                spec.getCertificationType(),
                spec.getDifficulty(),
                spec.getParticipantCount()
        );
    }
}
