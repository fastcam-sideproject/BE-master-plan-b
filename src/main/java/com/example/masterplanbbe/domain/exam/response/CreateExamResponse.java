package com.example.masterplanbbe.domain.exam.response;

import com.example.masterplanbbe.domain.exam.dto.SubjectDto;
import com.example.masterplanbbe.domain.exam.entity.Exam;
import com.example.masterplanbbe.domain.exam.enums.Category;
import com.example.masterplanbbe.domain.exam.enums.CertificationType;

import java.util.List;
import java.util.Objects;

public record CreateExamResponse(
        Long examId,
        String title,
        Category category,
        CertificationType certificationType,
        String authority,
        List<SubjectDto> subjects,
        String preparation,
        String eligibility,
        String examStructure,
        String passingCriteria
) {
    public CreateExamResponse(Exam exam){
        this(
                exam.getId(),
                exam.getTitle(),
                exam.getCategory(),
                exam.getCertificationType(),
                exam.getAuthority(),
                Objects.requireNonNull(exam.getSubjects()).stream()
                        .map(SubjectDto::new)
                        .toList(),
                exam.getExamDetail().getPreparation(),
                exam.getExamDetail().getEligibility(),
                exam.getExamDetail().getExamStructure(),
                exam.getExamDetail().getPassingCriteria()
        );
    }
}
