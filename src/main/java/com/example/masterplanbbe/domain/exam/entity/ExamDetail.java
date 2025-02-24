package com.example.masterplanbbe.domain.exam.entity;

import com.example.masterplanbbe.common.domain.FullAuditEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @OneToMany(mappedBy = "examDetail")
    private List<Subject> subjects;

}
