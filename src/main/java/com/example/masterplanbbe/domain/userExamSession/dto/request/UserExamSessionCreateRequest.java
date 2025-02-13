package com.example.masterplanbbe.domain.userExamSession.dto.request;

import java.time.LocalDate;
import java.time.LocalTime;

public record UserExamSessionCreateRequest(
        Long examId,
        LocalDate date,
        // NOTE : SwaggerConfig 에서 전역적으로 설정하는 방법도 존재.
//        @Schema(type = "string", example = "14:30:00")
//        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
        LocalTime startTime,
//        @Schema(type = "string", example = "14:30:00")
//        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
        LocalTime endTime
) {
}
