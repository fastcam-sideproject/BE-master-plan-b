package com.example.masterplanbbe.domain.exam.entity;

import com.example.masterplanbbe.common.annotation.NonNull;
import com.example.masterplanbbe.common.annotation.Nullable;
import com.example.masterplanbbe.common.domain.FullAuditEntity;
import com.example.masterplanbbe.domain.exam.request.ExamUpdateRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "exam_details")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ExamDetail extends FullAuditEntity {
    @NonNull
    @OneToOne
    private Exam exam;

    @Nullable
    @Column
    private String preparation;

    @Nullable
    @Column
    private String eligibility;

    @Nullable
    @Column
    private String examStructure;

    @Nullable
    @Column
    private String passingCriteria;

    @Builder
    public ExamDetail(@NonNull Exam exam,
                      @Nullable String preparation,
                      @Nullable String eligibility,
                      @Nullable String examStructure,
                      @Nullable String passingCriteria) {
        this.exam = exam;
        this.preparation = preparation;
        this.eligibility = eligibility;
        this.examStructure = examStructure;
        this.passingCriteria = passingCriteria;
    }

    public void update(ExamUpdateRequest request) {
        this.preparation = request.preparation();
        this.eligibility = request.eligibility();
        this.examStructure = request.examStructure();
        this.passingCriteria = request.passingCriteria();
    }
}
