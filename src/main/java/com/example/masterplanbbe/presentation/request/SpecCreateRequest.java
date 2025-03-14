package com.example.masterplanbbe.presentation.request;

import com.example.masterplanbbe.domain.entity.ExamDetail;
import com.example.masterplanbbe.domain.entity.Subject;
import com.example.masterplanbbe.domain.entity.Spec;
import com.example.masterplanbbe.domain.enums.SpecCategory;
import com.example.masterplanbbe.domain.enums.CertificationType;

import java.util.List;

public record SpecCreateRequest(
        String name,
        String issuingOrganization,
        SpecCategory specCategory,
        CertificationType certificationType,
        String preparation,
        String eligibility,
        String examStructure,
        String passingCriteria,
        List<SubjectCreateRequest> subjectCreateRequests
) {
    public Spec toSpec() {
        Spec spec = new Spec(
                name,
                issuingOrganization,
                specCategory,
                certificationType,
                0,
                null
        );

        ExamDetail examDetail = new ExamDetail(
                spec,
                preparation,
                eligibility,
                examStructure,
                passingCriteria,
                null,
                null
        );
        spec.addExamDetail(examDetail);

        List<Subject> subjects = subjectCreateRequests.stream()
                .map( it -> it.toEntity(examDetail))
                .toList();
        subjects.forEach(examDetail::addSubject);

        return spec;
    }

}
