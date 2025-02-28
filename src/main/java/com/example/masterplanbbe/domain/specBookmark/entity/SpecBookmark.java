package com.example.masterplanbbe.domain.specBookmark.entity;

import com.example.masterplanbbe.common.domain.IdAndCreatedEntity;
import com.example.masterplanbbe.domain.spec.entity.Spec;
import com.example.masterplanbbe.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "exam_bookmarks")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SpecBookmark extends IdAndCreatedEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "spec_id", nullable = false)
    private Spec spec;

    @Builder
    public SpecBookmark(Member member, Spec spec) {
        this.member = member;
        this.spec = spec;
    }
}
