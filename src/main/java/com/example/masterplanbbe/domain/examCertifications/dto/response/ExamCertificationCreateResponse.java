package com.example.masterplanbbe.domain.examCertifications.dto.response;

import com.example.masterplanbbe.domain.exam.entity.Exam;
import com.example.masterplanbbe.domain.examCertifications.entity.ExamCertification;
import com.example.masterplanbbe.domain.examCertifications.enums.PriorLearningLevel;
import com.example.masterplanbbe.member.entity.Member;

import java.time.LocalDateTime;


public record ExamCertificationCreateResponse(
        Long examCertificationId,
        PriorLearningLevel priorLearningLevel,
        Integer dailyStudyHours,
        String learningPeriod,
        Integer viewCount,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt,

        MemberResponse member,
        ExamResponse exam
) {
    public static ExamCertificationCreateResponse from(ExamCertification examCertification) {
        return new ExamCertificationCreateResponse(
                examCertification.getId(),
                examCertification.getPriorLearningLevel(),
                examCertification.getDailyStudyHours(),
                examCertification.getLearningPeriod(),
                examCertification.getViewCount(),
                examCertification.getCreatedAt(),
                examCertification.getModifiedAt(),
                MemberResponse.from(examCertification.getMember()),
                ExamResponse.from(examCertification.getExam())
        );
    }

    public record MemberResponse(
            Long memberId,
            String nickname,
            String profileImageUrl
    ) {
        public static MemberResponse from(Member member) {
            return new MemberResponse(
                    member.getId(),
                    member.getNickname(),
                    member.getProfileImageUrl()
            );
        }
    }

    public record ExamResponse(
            Long examId,
            String title
    ){
        public static ExamResponse from(Exam exam) {
            return new ExamResponse(
                    exam.getId(),
                    exam.getTitle()
            );
        }
    }
}
