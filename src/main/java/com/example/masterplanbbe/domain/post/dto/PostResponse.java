package com.example.masterplanbbe.domain.post.dto;

import com.example.masterplanbbe.domain.comment.dto.CommentResponse;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

public class PostResponse {

    @Builder
    public record Summary(
            Long postId,
            String title,
            String content,
            String nickname,
            LocalDateTime createdAt
    ) {}

    @Builder
    public record Detail(
            Long postId,
            String title,
            String content,
            String nickname,
            LocalDateTime createdAt,
            LocalDateTime modifiedAt,
            List<CommentResponse> comments
    ) {}
}
