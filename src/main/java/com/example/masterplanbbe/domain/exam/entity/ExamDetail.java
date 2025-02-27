package com.example.masterplanbbe.domain.exam.entity;

import com.example.masterplanbbe.common.domain.FullAuditEntity;
import com.example.masterplanbbe.domain.spec.entity.Spec;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "exam_details")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ExamDetail extends FullAuditEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "spec_id")
    private Spec spec;

    @Column(nullable = false)
    private String preparation;

    @Column(nullable = false)
    private String eligibility;

    @Column(nullable = false)
    private String examStructure;

    @Column(nullable = false)
    private String passingCriteria;

    @OneToMany(mappedBy = "examDetail", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Exam> exams;

    @OneToMany(mappedBy = "examDetail", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Subject> subjects;

    @Builder
    public ExamDetail(Spec spec,
                      String preparation,
                      String eligibility,
                      String examStructure,
                      String passingCriteria) {
        this.spec = spec;
        spec.addExamDetail(this);
        this.preparation = preparation;
        this.eligibility = eligibility;
        this.examStructure = examStructure;
        this.passingCriteria = passingCriteria;
        this.exams = new ArrayList<>();
        this.subjects = new ArrayList<>();
    }

    public void addExam(Exam exam) {
        this.exams.add(exam);
    }

    public void addSubject(Subject subject) {
        this.subjects.add(subject);
    }
}
