package com.example.masterplanbbe.exam.entity;

import com.example.masterplanbbe.common.annotation.NonNull;
import com.example.masterplanbbe.common.domain.BaseEntity;
import com.example.masterplanbbe.exam.enums.SessionType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;


@Entity
@Table(name = "exam_sessions")
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ExamSession extends BaseEntity {
    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exam_id")
    private Exam exam;

    @NonNull
    @Column
    private String location;

    @NonNull
    @Column
    private LocalDate date;

    @NonNull
    @Column
    private SessionType sessionType;
}
