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

    @Builder
    public Subject(String name,
                   ExamDetail examDetail) {
        this.name = name;
        this.examDetail = examDetail;
        examDetail.addSubject(this);
    }
}
