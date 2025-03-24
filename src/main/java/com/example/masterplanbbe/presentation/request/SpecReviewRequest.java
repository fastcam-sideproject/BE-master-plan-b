package com.example.masterplanbbe.presentation.request;

import com.example.masterplanbbe.domain.entity.Member;
import com.example.masterplanbbe.domain.entity.MemberSpec;
import com.example.masterplanbbe.domain.entity.Spec;
import com.example.masterplanbbe.domain.enums.*;

import com.example.masterplanbbe.domain.entity.SpecReview;

public record SpecReviewRequest(
        Difficulty difficulty,
        ExamType examType,
        ReflectionLevel reflectionLevel,
        LearningPeriod learningPeriod,
        DailyStudyTime dailyStudyTime,
        LearningLevel learningLevel,
        TimeSufficiency timeSufficiency,
        StudyMethod studyMethod,
        String tipTitle,
        String tipDescription
) {
    public SpecReview toEntity(Member member, MemberSpec memberSpec) {
        return new SpecReview(
                member,
                memberSpec,
                difficulty(),
                examType(),
                reflectionLevel(),
                learningPeriod(),
                dailyStudyTime(),
                learningLevel(),
                timeSufficiency(),
                studyMethod(),
                0,
                0,
                tipTitle(),
                tipDescription()

        );
    }
}

