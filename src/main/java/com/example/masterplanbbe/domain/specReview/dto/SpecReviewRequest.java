package com.example.masterplanbbe.domain.specReview.dto;

import com.example.masterplanbbe.domain.member.entity.Member;
import com.example.masterplanbbe.domain.spec.entity.Spec;
import com.example.masterplanbbe.domain.specReview.entity.SpecReview;
import com.example.masterplanbbe.domain.specReview.enums.*;

public record SpecReviewRequest(
        Difficulty difficulty,
        ExamType examType,
        ReflectionLevel reflectionLevel,
        LearningPeriod learningPeriod,
        DailyStudyTime dailyStudyTime,
        LearningLevel learningLevel,
        TimeSufficiency timeSufficiency,
        String studyMethod,
        String tipTitle,
        String tipDescription
) {
    public SpecReview toEntity(Member member, Spec spec) {
        return new SpecReview(
                member,
                spec,
                difficulty(),
                examType(),
                reflectionLevel(),
                learningPeriod(),
                dailyStudyTime(),
                learningLevel(),
                timeSufficiency(),
                0,
                0,
                studyMethod(),
                tipTitle(),
                tipDescription()

        );
    }
}

