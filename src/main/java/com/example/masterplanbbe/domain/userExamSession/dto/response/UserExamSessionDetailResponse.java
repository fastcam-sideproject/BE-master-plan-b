package com.example.masterplanbbe.domain.userExamSession.dto.response;

import com.example.masterplanbbe.domain.exam.enums.CertificationType;
import com.example.masterplanbbe.domain.userExamSession.entity.UserExamSession;
import com.querydsl.core.annotations.QueryProjection;

import java.time.LocalDate;

public record UserExamSessionDetailResponse(
        Long id,
        Long memberId,
        CertificationType certificationType,
        String title,
        LocalDate date,
        Long dueDate
) {
    public static UserExamSessionDetailResponse of(Long id, Long memberId, CertificationType certificationType, String title, LocalDate date, Long dueDate) {
        return new UserExamSessionDetailResponse(id, memberId, certificationType, title, date, dueDate);
    }

    public static UserExamSessionDetailResponse from(UserExamSession userExamSession, Long dueDate) {
        return UserExamSessionDetailResponse.of(
                userExamSession.getId(),
                userExamSession.getMember().getId(),
                userExamSession.getExam().getCertificationType(),
                userExamSession.getExam().getTitle(),
                userExamSession.getDate(),
                dueDate
        );
    }

    @QueryProjection
    public UserExamSessionDetailResponse(Long id, Long memberId, CertificationType certificationType, String title, LocalDate date, Long dueDate) {
        this.id = id;
        this.memberId = memberId;
        this.certificationType = certificationType;
        this.title = title;
        this.date = date;
        this.dueDate = dueDate;
    }
}
