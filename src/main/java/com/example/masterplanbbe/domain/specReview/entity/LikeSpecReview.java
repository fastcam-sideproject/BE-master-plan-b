package com.example.masterplanbbe.domain.post.entity;

import com.example.masterplanbbe.common.domain.FullAuditEntity;
import com.example.masterplanbbe.domain.member.entity.Member;
import com.example.masterplanbbe.domain.specReview.entity.SpecReview;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "spec_like",
        uniqueConstraints = {@UniqueConstraint(name = "unique_spec_like", columnNames = {"specreview_id", "member_id"})})
public class LikeSpecReview extends FullAuditEntity {

    @ManyToOne
    @JoinColumn(name = "specreview_id",nullable = false)
    private SpecReview specReview;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id",nullable = false)
    private Member member;
}
