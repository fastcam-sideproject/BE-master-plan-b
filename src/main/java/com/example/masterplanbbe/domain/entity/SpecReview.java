package com.example.masterplanbbe.domain.entity;

import com.example.masterplanbbe.domain.common.FullAuditEntity;
import com.example.masterplanbbe.domain.enums.*;
import com.example.masterplanbbe.presentation.request.SpecReviewRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(
        name = "reviews",
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
    private LearningPeriod learningPeriod;

    @Enumerated(EnumType.STRING)
    private DailyStudyTime dailyStudyTime;

    @Enumerated(EnumType.STRING)
    private LearningLevel learningLevel;

    @Enumerated(EnumType.STRING)
    private TimeSufficiency timeSufficiency;

    @Enumerated(EnumType.STRING)
    private StudyMethod studyMethod;

    @Column(nullable = false)
    private Integer viewCount = 0;

    @Column(nullable = false)
    private Integer likeCount = 0;

    private String tipTitle;

    private String tipDescription;

    public void updateReview(SpecReviewRequest specReviewRequest) {
        this.difficulty = specReviewRequest.difficulty();
        this.examType = specReviewRequest.examType();
        this.reflectionLevel = specReviewRequest.reflectionLevel();
        this.learningPeriod = specReviewRequest.learningPeriod();
        this.learningLevel = specReviewRequest.learningLevel();
        this.dailyStudyTime = specReviewRequest.dailyStudyTime();
        this.timeSufficiency = specReviewRequest.timeSufficiency();
        this.studyMethod = specReviewRequest.studyMethod();
        this.tipTitle = specReviewRequest.tipTitle();
        this.tipDescription = specReviewRequest.tipDescription();
    }

    public void addViewCount() {
        this.viewCount += 1;
    }

    public void updateLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }
}
