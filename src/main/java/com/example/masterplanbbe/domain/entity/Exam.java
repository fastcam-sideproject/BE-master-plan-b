package com.example.masterplanbbe.domain.entity;

import com.example.masterplanbbe.domain.common.FullAuditEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "exams")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Exam extends FullAuditEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exam_detail_id")
    private ExamDetail examDetail;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer participantCount;

    @Column(name = "apply_start_date")
    private LocalDate applyStartDate;

    @Column(name = "apply_end_date")
    private LocalDate applyEndDate;

    @Column(name = "exam_start_date")
    private LocalDate examStartDate;

    public Exam(ExamDetail examDetail,
                String name,
                Integer participantCount,
                LocalDate applyStartDate,
                LocalDate applyEndDate,
                LocalDate examStartDate) {
        this.examDetail = examDetail;
        examDetail.addExam(this);
        this.name = name;
        this.participantCount = participantCount;
        this.applyStartDate = applyStartDate;
        this.applyEndDate = applyEndDate;
        this.examStartDate = examStartDate;
    }

    public void update(String name,
                       Integer participantCount,
                       LocalDate applyStartDate,
                       LocalDate applyEndDate,
                       LocalDate examStartDate) {
        this.name = name;
        this.participantCount = participantCount;
        this.applyStartDate = applyStartDate;
        this.applyEndDate = applyEndDate;
        this.examStartDate = examStartDate;
    }
}