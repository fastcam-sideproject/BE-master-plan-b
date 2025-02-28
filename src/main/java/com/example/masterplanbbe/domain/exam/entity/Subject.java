package com.example.masterplanbbe.domain.exam.entity;

import com.example.masterplanbbe.common.domain.FullAuditEntity;
import com.example.masterplanbbe.domain.spec.entity.Spec;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "subjects")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Subject extends FullAuditEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exam_detail_id")
    private ExamDetail examDetail;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @Builder
    public Subject(
            ExamDetail examDetail,
            String name,
            String description
    ) {
        this.examDetail = examDetail;
        examDetail.addSubject(this);
        this.name = name;
        this.description = description;
    }
}
