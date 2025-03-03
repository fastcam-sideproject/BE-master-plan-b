package com.example.masterplanbbe.domain.specReview.dto;

import com.example.masterplanbbe.domain.specReview.entity.SpecReview;
import com.example.masterplanbbe.domain.specReview.enums.*;

public record SpecReviewResponse(
        Long id,
        Long memberId,
        Long specId,
        Difficulty difficulty,
        ExamType examType,
        ReflectionLevel reflectionLevel,
        StudyDuration studyDuration,
        LearningLevel learningLevel,
        TimeSufficiency timeSufficiency,
        Integer viewCount,
        String studyMethod,
        String tipTitle,
        String tipDescription
) {
    public static SpecReviewResponse from(SpecReview specReview) {
        return new SpecReviewResponse(
                specReview.getId(),
                specReview.getMember().getId(),
                specReview.getSpec().getId(),
                specReview.getDifficulty(),
                specReview.getExamType(),
                specReview.getReflectionLevel(),
                specReview.getStudyDuration(),
                specReview.getLearningLevel(),
                specReview.getTimeSufficiency(),
                specReview.getViewCount(),
                specReview.getStudyMethod(),
                specReview.getTipTitle(),
                specReview.getTipDescription()
        );
    }
}