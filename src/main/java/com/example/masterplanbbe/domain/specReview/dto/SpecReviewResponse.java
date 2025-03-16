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
        LearningPeriod learningPeriod,
        LearningLevel learningLevel,
        TimeSufficiency timeSufficiency,
        Integer viewCount,
        Integer likeCount,
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
                specReview.getLearningPeriod(),
                specReview.getLearningLevel(),
                specReview.getTimeSufficiency(),
                specReview.getViewCount(),
                specReview.getLikeCount(),
                specReview.getStudyMethod(),
                specReview.getTipTitle(),
                specReview.getTipDescription()
        );
    }
}