package com.example.masterplanbbe.studyLog.response;

import com.example.masterplanbbe.studyLog.entity.StudyLog;
import com.example.masterplanbbe.studyLog.enums.InputSource;

import java.time.LocalDate;


public record CreateStudyLogResponse(
        Long id,
        LocalDate studyDate,
        String subject,
        Integer elapsedTime,
        String title,
        String content,
        InputSource inputSource
) {
    public static CreateStudyLogResponse of(StudyLog studyLog) {
        return new CreateStudyLogResponse (
                studyLog.getId(),
                studyLog.getStudyDate(),
                studyLog.getSubject(),
                studyLog.getElapsedTime(),
                studyLog.getTitle(),
                studyLog.getContent(),
                studyLog.getInputSource()
        );
    }
}
