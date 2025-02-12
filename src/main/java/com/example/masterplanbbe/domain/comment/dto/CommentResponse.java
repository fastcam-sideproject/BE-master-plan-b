package com.example.masterplanbbe.domain.comment.dto;

import com.example.masterplanbbe.domain.comment.entity.Comment;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record CommentResponse(
        Long commentId,
        String content,
        String nickname,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {
    public static CommentResponse from(Comment comment) {
        return CommentResponse.builder()
                .commentId(comment.getId())
                .content(comment.getContent())
                .nickname(comment.getMember().getNickname())
                .createdAt(comment.getCreatedAt())
                .modifiedAt(comment.getModifiedAt())
                .build();
    }
}
