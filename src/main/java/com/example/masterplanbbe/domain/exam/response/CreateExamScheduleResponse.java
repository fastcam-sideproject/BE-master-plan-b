package com.example.masterplanbbe.domain.exam.response;

import java.time.LocalDate;

public record CreateExamScheduleResponse(
        Long id,
        Long examId,
        String location,
        LocalDate date,
        String sessionType
) {
    public static CreateExamScheduleResponse of(Long id, Long examId, String location, LocalDate date, String sessionType) {
        return new CreateExamScheduleResponse(id, examId, location, date, sessionType);
    }
}
