package com.example.masterplanbbe.domain.exam.dto;

import com.example.masterplanbbe.domain.exam.entity.Exam;
import com.example.masterplanbbe.domain.exam.enums.Category;
import com.example.masterplanbbe.domain.exam.enums.CertificationType;
import com.querydsl.core.annotations.QueryProjection;

public record ExamWithDetailsDto(

) {
    @QueryProjection
    public ExamWithDetailsDto(Exam exam) {
       this();
    }
}
