package com.example.masterplanbbe.domain.studyLog.entity;

import com.example.masterplanbbe.common.annotation.NonNull;
import com.example.masterplanbbe.common.annotation.Nullable;
import com.example.masterplanbbe.common.domain.FullAuditEntity;
import com.example.masterplanbbe.domain.studyLog.enums.InputSource;
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
public class StudyLog extends FullAuditEntity {
    @NonNull
    @Column
    private LocalDate studyDate;

/*
    @ManyToOne
    @JoinColumn(name = "exam_id")
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

