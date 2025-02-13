package com.example.masterplanbbe.domain.userExamSession.dto.request;

import java.time.LocalDate;
import java.time.LocalTime;

public record UserExamSessionRequest(
        Long examId,
        LocalDate date,
        LocalTime startTime,
        LocalTime endTime
) {
}
