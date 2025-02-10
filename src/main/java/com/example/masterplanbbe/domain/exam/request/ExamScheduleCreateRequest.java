package com.example.masterplanbbe.domain.exam.request;

import java.time.LocalDate;

public record ExamScheduleCreateRequest(
    Long examId,
    String location,
    LocalDate date,
    String sessionType
) {
}
