package com.example.masterplanbbe.domain.specReview.entity;

import com.example.masterplanbbe.common.domain.FullAuditEntity;
import com.example.masterplanbbe.common.domain.IdAndCreatedEntity;
import com.example.masterplanbbe.domain.member.entity.Member;
import com.example.masterplanbbe.domain.spec.entity.Spec;
import com.example.masterplanbbe.domain.specReview.dto.SpecReviewRequest;
import com.example.masterplanbbe.domain.specReview.enums.*;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(
        name = "spec_review",
        uniqueConstraints = {
                @UniqueConstraint(name = "UK_spec_review_member", columnNames = { "spec_id", "member_id" })
        })
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class SpecReview extends FullAuditEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
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

    public void updateReview(SpecReviewRequest specReviewRequest) {
        this.difficulty = specReviewRequest.difficulty();
        this.examType = specReviewRequest.examType();
        this.reflectionLevel = specReviewRequest.reflectionLevel();
        this.studyDuration = specReviewRequest.studyDuration();
        this.learningLevel = specReviewRequest.learningLevel();
        this.timeSufficiency = specReviewRequest.timeSufficiency();
        this.viewCount = specReviewRequest.viewCount();
        this.studyMethod = specReviewRequest.studyMethod();
        this.tipTitle = specReviewRequest.tipTitle();
        this.tipDescription = specReviewRequest.tipDescription();
    }

    public void addViewCount() {
        this.viewCount += 1;
    }
}
