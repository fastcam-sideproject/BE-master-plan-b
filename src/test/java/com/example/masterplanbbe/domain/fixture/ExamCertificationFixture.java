package com.example.masterplanbbe.domain.fixture;

import com.example.masterplanbbe.domain.exam.entity.Exam;
import com.example.masterplanbbe.domain.examCertifications.dto.request.ExamCertificationCreateRequest;
import com.example.masterplanbbe.domain.examCertifications.entity.ExamCertification;
import com.example.masterplanbbe.domain.examCertifications.enums.PriorLearningLevel;
import com.example.masterplanbbe.member.entity.Member;

public class ExamCertificationFixture {
    public static ExamCertification createExamCertification() {
        return ExamCertification.builder()
                .learningPeriod("6달")
                .dailyStudyHours(6)
                .priorLearningLevel(PriorLearningLevel.NON_MAJOR)
                .viewCount(0)
                .build();
    }

    public static ExamCertification createExamCertificationWithMemberAndExam(Member member, Exam exam) {
        return ExamCertification.builder()
                .learningPeriod("6달")
                .dailyStudyHours(6)
                .priorLearningLevel(PriorLearningLevel.NON_MAJOR)
                .viewCount(0)
                .member(member)
                .exam(exam)
                .build();
    }

    public static ExamCertification createExamCertificationWithMemberAndExam(
            ExamCertificationCreateRequest createRequest, Member member, Exam exam) {
        return ExamCertification.builder()
                .learningPeriod(createRequest.learningPeriod())
                .dailyStudyHours(createRequest.dailyStudyHours())
                .priorLearningLevel(createRequest.priorLearningLevel())
                .viewCount(0)
                .member(member)
                .exam(exam)
                .build();
    }

}
