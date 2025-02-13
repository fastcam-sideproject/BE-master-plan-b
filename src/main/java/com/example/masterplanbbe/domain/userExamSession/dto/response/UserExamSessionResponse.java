package com.example.masterplanbbe.domain.userExamSession.dto.response;

import com.example.masterplanbbe.domain.userExamSession.entity.UserExamSession;

import java.time.LocalDate;
import java.time.LocalTime;

public record UserExamSessionResponse(
        Long id,
        String examTitle,
        LocalDate date,
        LocalTime startTime,
        LocalTime endTime
) {
    public static UserExamSessionResponse of(Long id, String examTitle, LocalDate date, LocalTime startTime, LocalTime endTime) {
        return new UserExamSessionResponse(id, examTitle, date, startTime, endTime);
    }

    public static UserExamSessionResponse from(UserExamSession userExamSession) {
        return UserExamSessionResponse.of(
                userExamSession.getId(),
                userExamSession.getExam().getTitle(),
                userExamSession.getDate(),
                userExamSession.getStartTime(),
                userExamSession.getEndTime()
        );
    }
}
