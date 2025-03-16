package com.example.masterplanbbe.domain.entity;

import com.example.masterplanbbe.domain.common.IdAndCreatedEntity;
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
