package com.example.masterplanbbe.presentation.request;

import com.example.masterplanbbe.domain.entity.ExamDetail;
import com.example.masterplanbbe.domain.entity.Subject;

public record SubjectCreateRequest(
        String name,
        String description
) {
    public Subject toEntity(ExamDetail examDetail) {
        return new Subject(examDetail, name, description);
    }
}
