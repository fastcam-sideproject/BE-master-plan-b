package com.example.masterplanbbe.domain.specReview.entity;

import com.example.masterplanbbe.common.domain.IdAndCreatedEntity;
import com.example.masterplanbbe.domain.member.entity.Member;
import com.example.masterplanbbe.domain.spec.entity.Spec;
import com.example.masterplanbbe.domain.specReview.enums.*;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "spec_review")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class SpecReview extends IdAndCreatedEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "spec_id", nullable = false)
    private Spec spec;

    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    @Enumerated(EnumType.STRING)
    private ExamType examType;

    @Enumerated(EnumType.STRING)
    private ReflectionLevel reflectionLevel;

    @Enumerated(EnumType.STRING)
    private StudyDuration studyDuration;

    @Enumerated(EnumType.STRING)
    private LearningLevel learningLevel;

    @Enumerated(EnumType.STRING)
    private TimeSufficiency timeSufficiency;

    private Integer viewCount;

    private String studyMethod;

    private String tipTitle;

    private String tipDescription;
}
