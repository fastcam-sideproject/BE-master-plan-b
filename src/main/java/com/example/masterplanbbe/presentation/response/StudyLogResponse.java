package com.example.masterplanbbe.presentation.response;

import com.example.masterplanbbe.domain.entity.StudyLog;
import com.example.masterplanbbe.domain.enums.InputSource;
import com.querydsl.core.annotations.QueryProjection;

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

    @QueryProjection
    public StudyLogResponse(Long id, LocalDate studyDate, Integer totalStudyTimes, String content, InputSource inputSource) {
        this.id = id;
        this.studyDate = studyDate;
        this.totalStudyTimes = totalStudyTimes;
        this.content = content;
        this.inputSource = inputSource;
    }
}
