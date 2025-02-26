package com.example.masterplanbbe.domain.studyLog.entity;

import com.example.masterplanbbe.common.domain.FullAuditEntity;
import com.example.masterplanbbe.domain.exam.entity.Exam;
import com.example.masterplanbbe.domain.studyLog.enums.InputSource;
import com.example.masterplanbbe.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "study_logs")
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudyLog extends FullAuditEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exam_id", nullable = false)
    private Exam exam;

    @Column(name = "date", nullable = false)
    private LocalDate studyDate;

    @Column(name = "total_study_times", nullable = false)
    private Integer totalStudyTimes;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "input_source", nullable = false)
    @Enumerated(EnumType.STRING)
    private InputSource inputSource;

    public static StudyLog of(Member member, Exam exam, LocalDate studyDate, Integer hour, Integer minutes, String content, InputSource inputSource) {
        Integer totalStudyTimes = hour * 60 + minutes;
        return new StudyLog(member, exam, studyDate, totalStudyTimes, content, inputSource);
    }
}

