package com.example.masterplanbbe.domain.studyLog.request;

import com.example.masterplanbbe.domain.studyLog.enums.InputSource;

import java.time.LocalDate;

public record StudyLogRequest(
        Long examId,
        LocalDate studyDate,
        Integer hour,
        Integer minute,
        String content,
        InputSource inputSource
) {
}
