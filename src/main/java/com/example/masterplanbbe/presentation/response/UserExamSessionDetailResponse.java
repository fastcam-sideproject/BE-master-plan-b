package com.example.masterplanbbe.presentation.response;

import com.example.masterplanbbe.domain.enums.CertificationType;
import com.example.masterplanbbe.domain.entity.UserExamSession;
import com.querydsl.core.annotations.QueryProjection;

import java.time.LocalDate;

public record UserExamSessionDetailResponse(
        Long id,
        String memberId,
        CertificationType certificationType,
        String title,
        LocalDate date,
        Long dueDate
) {
    public static UserExamSessionDetailResponse of(Long id, String memberId, CertificationType certificationType, String title, LocalDate date, Long dueDate) {
        return new UserExamSessionDetailResponse(id, memberId, certificationType, title, date, dueDate);
    }

    public static UserExamSessionDetailResponse from(UserExamSession userExamSession, Long dueDate) {
        return UserExamSessionDetailResponse.of(
                userExamSession.getId(),
                userExamSession.getMember().getEmail(),
                userExamSession.getExam().getExamDetail().getSpec().getCertificationType(),
                userExamSession.getExam().getName(),
                userExamSession.getExam().getExamStartDate(),
                dueDate
        );
    }

    @QueryProjection
    public UserExamSessionDetailResponse(Long id, String memberId, CertificationType certificationType, String title, LocalDate date, Long dueDate) {
        this.id = id;
        this.memberId = memberId;
        this.certificationType = certificationType;
        this.title = title;
        this.date = date;
        this.dueDate = dueDate;
    }
}
