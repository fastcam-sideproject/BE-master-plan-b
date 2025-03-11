package com.example.masterplanbbe.domain.studyLog.request;

import com.example.masterplanbbe.domain.studyLog.enums.InputSource;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

public record StudyLogRequest(
        Long examId,
        LocalDate studyDate,
        Integer hour,
        Integer minute,
        String content,
        @Schema(description = "검색 타입 (COMPUTER, MOBILE)", example = "COMPUTER", allowableValues = {"COMPUTER", "MOBILE"})
        InputSource inputSource
) {
}
