package com.example.masterplanbbe.domain.exam.response;

import com.example.masterplanbbe.domain.exam.dto.SubjectDto;
import com.example.masterplanbbe.domain.exam.entity.ExamDetail;
import com.example.masterplanbbe.domain.exam.entity.Spec;
import com.example.masterplanbbe.domain.exam.enums.Category;
import com.example.masterplanbbe.domain.exam.enums.CertificationType;

import java.util.List;
import java.util.Objects;

public record CreateSpecResponse(
        Long specId,
        String name,
        Category category,
        CertificationType certificationType,
        String issuingOrganization,
        List<SubjectDto> subjects,
        String preparation,
        String eligibility,
        String examStructure,
        String passingCriteria
) {
    public CreateSpecResponse(Spec spec, ExamDetail examDetail){
        this(
                spec.getId(),
                spec.getName(),
                spec.getCategory(),
                spec.getCertificationType(),
                spec.getIssuingOrganization(),
                Objects.requireNonNull(examDetail.getSubjects()).stream()
                        .map(SubjectDto::new)
                        .toList(),
                examDetail.getPreparation(),
                examDetail.getEligibility(),
                examDetail.getExamStructure(),
                examDetail.getPassingCriteria()
        );
    }
}
