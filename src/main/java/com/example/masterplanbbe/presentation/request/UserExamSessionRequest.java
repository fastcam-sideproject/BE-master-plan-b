package com.example.masterplanbbe.presentation.request;

import java.time.LocalTime;

public record UserExamSessionRequest(
        Long examId,
        LocalTime startTime,
        LocalTime endTime
) {
}
