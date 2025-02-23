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

}
