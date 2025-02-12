package com.example.masterplanbbe.domain.comment.dto;

import com.example.masterplanbbe.domain.comment.entity.Comment;
import com.example.masterplanbbe.domain.post.entity.Post;
import com.example.masterplanbbe.member.entity.Member;
import lombok.Builder;

public record CommentRequest(
        Long postId,
        Long memberId,
        String content
) {
    @Builder
    public Comment toEntity(Post post, Member member) {
        return Comment.builder()
                .content(content)
                .member(member)
                .post(post)
                .build();
    }
}
