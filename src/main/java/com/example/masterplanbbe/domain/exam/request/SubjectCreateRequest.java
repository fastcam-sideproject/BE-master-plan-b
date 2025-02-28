package com.example.masterplanbbe.domain.exam.request;

import com.example.masterplanbbe.domain.exam.entity.ExamDetail;
import com.example.masterplanbbe.domain.exam.entity.Subject;

public record SubjectCreateRequest(
        String name,
        String description
) {
    public Subject toEntity(ExamDetail examDetail) {
        return new Subject(examDetail, name, description);
    }
}
