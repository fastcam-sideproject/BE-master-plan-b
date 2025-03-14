package com.example.masterplanbbe.application.dto;


import com.example.masterplanbbe.domain.entity.ExamDetail;
import com.example.masterplanbbe.domain.entity.Spec;

public record ExamDetailDto(
        String preparation,
        String eligibility,
        String examStructure,
        String passingCriteria
) {
    public ExamDetail toEntity(Spec spec) {
        return new ExamDetail(
                spec,
                preparation,
                eligibility,
                examStructure,
                passingCriteria,
                null,
                null
        );
    }
}
