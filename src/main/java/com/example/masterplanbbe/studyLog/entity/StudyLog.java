package com.example.masterplanbbe.studyLog.entity;

import com.example.masterplanbbe.common.annotation.NonNull;
import com.example.masterplanbbe.common.annotation.Nullable;
import com.example.masterplanbbe.common.domain.BaseEntity;
import com.example.masterplanbbe.studyLog.enums.InputSource;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "study_log")
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudyLog extends BaseEntity {
    @NonNull
    @Column
    private LocalDate studyDate;

/*
    @Column
    private Exam exam;
*/

    @Nullable
    @Column
    private String subject;

    @Nullable
    @Column
    private Integer elapsedTime;

    @Column
    private String title;

    @Column
    private String content;

    @NonNull
    @Column
    @Enumerated(EnumType.STRING)
    private InputSource inputSource;
}

