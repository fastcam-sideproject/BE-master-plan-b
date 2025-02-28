package com.example.masterplanbbe.domain.exam.entity;

import com.example.masterplanbbe.common.domain.FullAuditEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "exams")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Exam extends FullAuditEntity {
    @ManyToOne
    @JoinColumn(name = "exam_detail_id")
    private ExamDetail examDetail;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Double difficulty;

    @Column(nullable = false)
    private Integer participantCount;

    @Column(nullable = false)
    private LocalDate applyStartDate;

    @Column(nullable = false)
    private LocalDate applyEndDate;

    @Column(nullable = false)
    private LocalDate examStartDate;

    @Builder
    public Exam(ExamDetail examDetail,
                String name,
                Double difficulty,
                Integer participantCount,
                LocalDate applyStartDate,
                LocalDate applyEndDate,
                LocalDate examStartDate) {
        this.examDetail = examDetail;
        examDetail.addExam(this);
        this.name = name;
        this.difficulty = difficulty;
        this.participantCount = participantCount;
        this.applyStartDate = applyStartDate;
        this.applyEndDate = applyEndDate;
        this.examStartDate = examStartDate;
    }

    public void update(String name,
                       Double difficulty,
                       Integer participantCount,
                       LocalDate applyStartDate,
                       LocalDate applyEndDate,
                       LocalDate examStartDate) {
        this.name = name;
        this.difficulty = difficulty;
        this.participantCount = participantCount;
        this.applyStartDate = applyStartDate;
        this.applyEndDate = applyEndDate;
        this.examStartDate = examStartDate;
    }
}