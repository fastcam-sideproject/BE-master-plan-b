package com.example.masterplanbbe.domain.exam.dto;

import com.example.masterplanbbe.domain.exam.entity.ExamDetail;

public record ExamDetailDto(
        String preparation,
        String eligibility,
        String examStructure,
        String passingCriteria
) {
    public ExamDetailDto(ExamDetail examDetail) {
        this(examDetail.getPreparation(), examDetail.getEligibility(), examDetail.getExamStructure(), examDetail.getPassingCriteria());
    }

    public ExamDetail toEntity() {
        return ExamDetail.builder()
                .preparation(preparation)
                .eligibility(eligibility)
                .examStructure(examStructure)
                .passingCriteria(passingCriteria)
                .build();
    }
}
