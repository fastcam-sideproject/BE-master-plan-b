package com.example.masterplanbbe.domain.studyLog.request;

import com.example.masterplanbbe.domain.studyLog.entity.StudyLog;
import com.example.masterplanbbe.domain.studyLog.enums.InputSource;

import java.time.LocalDate;

public record CreateStudyLogRequest(
        LocalDate studyDate,
        String subject,
        Integer elapsedTime,
        String title,
        String content,
        InputSource inputSource
) {
    public StudyLog toEntity() {
        return new StudyLog(
                studyDate,
                subject,
                elapsedTime,
                title,
                content,
                inputSource
        );
    }
}
