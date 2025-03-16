package com.example.masterplanbbe.domain.entity;

import com.example.masterplanbbe.domain.common.FullAuditEntity;
import com.example.masterplanbbe.presentation.request.UserExamSessionRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Entity
@Table(name = "user_exam_sessions")
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserExamSession extends FullAuditEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exam_id", nullable = false)
    private Exam exam;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Member member;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    public void updateUserExamSession(UserExamSessionRequest request, Exam exam) {
        this.exam = exam;
        this.startTime = request.startTime();
        this.endTime = request.endTime();
    }

    public static UserExamSession of(Exam exam, Member member, LocalTime startTime, LocalTime endTime) {
        return new UserExamSession(exam, member, startTime, endTime);
    }
}
