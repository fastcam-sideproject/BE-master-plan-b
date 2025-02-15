package com.example.masterplanbbe.domain.post.entity;

import com.example.masterplanbbe.common.domain.FullAuditEntity;
import com.example.masterplanbbe.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

@Entity(name = "likes")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "post_like",
        uniqueConstraints = {@UniqueConstraint(name = "unique_post_like", columnNames = {"post_id", "member_id"})})
public class Like extends FullAuditEntity {

    @ManyToOne
    @JoinColumn(name = "post_id",nullable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id",nullable = false)
    private Member member;
}
