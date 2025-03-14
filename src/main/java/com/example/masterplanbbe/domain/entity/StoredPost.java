package com.example.masterplanbbe.domain.entity;

import com.example.masterplanbbe.domain.common.FullAuditEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "stored_posts",
        uniqueConstraints = {@UniqueConstraint(name = "unique_member_post", columnNames = {"post_id", "member_id"})})
public class StoredPost extends FullAuditEntity {

    @ManyToOne
    @JoinColumn(name = "post_id",nullable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id",nullable = false)
    private Member member;

    @Builder
    public StoredPost(Member member, Post post) {
        this.member = member;
        this.post = post;
    }
}
