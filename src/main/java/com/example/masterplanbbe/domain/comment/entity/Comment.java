package com.example.masterplanbbe.domain.comment.entity;

import com.example.masterplanbbe.common.domain.FullAuditEntity;
import com.example.masterplanbbe.domain.post.entity.Post;
import com.example.masterplanbbe.member.entity.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Comment extends FullAuditEntity {

    private String content;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    public void setPost(Post post) {
        this.post = post;
    }

    public void updateComment(String content) {
        this.content = content;
    }
}
