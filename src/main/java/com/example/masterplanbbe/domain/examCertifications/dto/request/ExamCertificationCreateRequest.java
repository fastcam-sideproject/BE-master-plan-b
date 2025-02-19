package com.example.masterplanbbe.domain.examCertifications.dto.request;

import com.example.masterplanbbe.domain.exam.entity.Exam;
import com.example.masterplanbbe.domain.examCertifications.entity.ExamCertification;
import com.example.masterplanbbe.domain.examCertifications.enums.PriorLearningLevel;
import com.example.masterplanbbe.member.entity.Member;

public record ExamCertificationCreateRequest(
        PriorLearningLevel priorLearningLevel,
        Integer dailyStudyHours,
        String learningPeriod
) {
    public ExamCertification toEntity(Exam exam, Member member) {
        return ExamCertification.builder()
                .priorLearningLevel(priorLearningLevel)
                .dailyStudyHours(dailyStudyHours)
                .learningPeriod(learningPeriod)
                .viewCount(0)
                .exam(exam)
                .member(member)
                .build();
    }
}
