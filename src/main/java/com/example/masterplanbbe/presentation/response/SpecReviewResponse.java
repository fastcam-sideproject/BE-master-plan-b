package com.example.masterplanbbe.presentation.response;

import com.example.masterplanbbe.domain.entity.SpecReview;
import com.example.masterplanbbe.domain.enums.*;

public record SpecReviewResponse(
        Long id,
        Long memberId,
        Long memberSpecId,
        Difficulty difficulty,
        ExamType examType,
        ReflectionLevel reflectionLevel,
        LearningPeriod learningPeriod,
        LearningLevel learningLevel,
        TimeSufficiency timeSufficiency,
        Integer viewCount,
        Integer likeCount,
        StudyMethod studyMethod,
        String tipTitle,
        String tipDescription
) {
    public static SpecReviewResponse from(SpecReview specReview) {
        return new SpecReviewResponse(
                specReview.getId(),
                specReview.getMember().getId(),
                specReview.getMemberSpec().getId(),
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