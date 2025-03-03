package com.example.masterplanbbe.domain.specReview.dto;

import com.example.masterplanbbe.domain.member.entity.Member;
import com.example.masterplanbbe.domain.spec.entity.Spec;
import com.example.masterplanbbe.domain.specReview.entity.SpecReview;
import com.example.masterplanbbe.domain.specReview.enums.*;

public record SpecReviewRequest(
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
    public SpecReview toEntity(Member member, Spec spec) {
        return new SpecReview(
                member,
                spec,
                difficulty,
                examType,
                reflectionLevel,
                studyDuration,
                learningLevel,
                timeSufficiency,
                viewCount,
                studyMethod,
                tipTitle,
                tipDescription
        );
    }
}

