package com.example.masterplanbbe.domain.exam.request;

import com.example.masterplanbbe.domain.exam.dto.SubjectDto;
import com.example.masterplanbbe.domain.exam.entity.Exam;
import com.example.masterplanbbe.domain.exam.entity.Subject;
import com.example.masterplanbbe.domain.exam.enums.Category;
import com.example.masterplanbbe.domain.exam.enums.CertificationType;

import java.util.List;

public record ExamCreateRequest(
        String title,
        Category category,
        String authority,
        CertificationType certificationType,
        List<SubjectDto> subjectDtoList
) {
    public Exam toEntity() {
        return new Exam(
                title,
                category,
                authority,
                0.0,
                0,
                certificationType,
                subjectDtoList != null ?
                        subjectDtoList.stream().map(SubjectDto::toEntity).toList() :
                        List.of()
        );
    }
}
