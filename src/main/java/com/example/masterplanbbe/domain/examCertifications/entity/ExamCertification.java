package com.example.masterplanbbe.domain.examCertifications.entity;

import com.example.masterplanbbe.common.domain.FullAuditEntity;
import com.example.masterplanbbe.domain.exam.entity.Exam;
import com.example.masterplanbbe.domain.examCertifications.enums.PriorLearningLevel;
import com.example.masterplanbbe.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class ExamCertification extends FullAuditEntity {

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PriorLearningLevel priorLearningLevel;

    @Column(nullable = false)
    private Integer dailyStudyHours;

    @Column(nullable = false)
    private String learningPeriod;

    @Column(nullable = false)
    private Integer viewCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exam_id", nullable = false)
    private Exam exam;
}
