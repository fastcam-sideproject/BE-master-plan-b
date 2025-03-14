package com.example.masterplanbbe.domain.spec.request;

import com.example.masterplanbbe.domain.exam.entity.ExamDetail;
import com.example.masterplanbbe.domain.exam.entity.Subject;
import com.example.masterplanbbe.domain.exam.request.SubjectCreateRequest;
import com.example.masterplanbbe.domain.spec.entity.Spec;
import com.example.masterplanbbe.domain.exam.enums.Category;
import com.example.masterplanbbe.domain.exam.enums.CertificationType;

import java.util.List;

public record SpecCreateRequest(
        String name,
        String issuingOrganization,
        Category category,
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
                category,
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
