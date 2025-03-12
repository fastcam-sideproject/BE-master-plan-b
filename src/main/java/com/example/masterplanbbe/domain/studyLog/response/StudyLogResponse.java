package com.example.masterplanbbe.domain.studyLog.response;

import com.example.masterplanbbe.domain.studyLog.entity.StudyLog;
import com.example.masterplanbbe.domain.studyLog.enums.InputSource;

import java.time.LocalDate;


public record StudyLogResponse(
        Long id,
        LocalDate studyDate,
        Integer totalStudyTimes,
        String content,
        InputSource inputSource
) {
    public static StudyLogResponse of(Long id, LocalDate studyDate, Integer totalStudyTimes, String content, InputSource inputSource) {
        return new StudyLogResponse(id, studyDate, totalStudyTimes, content, inputSource);
    }

    public static StudyLogResponse from(StudyLog studyLog) {
        return StudyLogResponse.of(
                studyLog.getId(),
                studyLog.getStudyDate(),
                studyLog.getTotalStudyTimes(),
                studyLog.getContent(),
                studyLog.getInputSource()
        );
    }
}
