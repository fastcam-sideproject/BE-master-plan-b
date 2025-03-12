package com.example.masterplanbbe.domain.userExamSession.dto.request;

import java.time.LocalTime;

public record UserExamSessionRequest(
        Long examId,
        LocalTime startTime,
        LocalTime endTime
) {
}
