package com.example.masterplanbbe.presentation.request;

import com.example.masterplanbbe.domain.entity.Comment;
import com.example.masterplanbbe.domain.entity.Post;
import com.example.masterplanbbe.domain.entity.Member;
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
