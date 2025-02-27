package com.example.masterplanbbe.domain.post.dto;

import com.example.masterplanbbe.domain.comment.dto.CommentResponse;
import com.example.masterplanbbe.domain.post.entity.Category;
import com.example.masterplanbbe.domain.post.entity.Post;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

public class PostResponse {
    public record Summary(
            Long postId,
            String title,
            String content,
            String nickname,
            LocalDateTime createdAt,
            Category category,
            Integer likeCount,
            Integer viewCount,
            Integer commentCount
    ) {
        public static Summary from(Post post) {
            return new Summary(
                    post.getId(),
                    post.getTitle(),
                    post.getContent(),
                    post.getMember().getNickname(),
                    post.getCreatedAt(),
                    post.getCategory(),
                    post.getViewCount(),
                    post.getLikeCount(),
                    post.getCommentList().size()
            );
        }
    }

    public record Detail(
            Long postId,
            String title,
            String content,
            String nickname,
            Category category,
            Integer viewCount,
            Integer likeCount,
            LocalDateTime createdAt,
            LocalDateTime modifiedAt,
            List<CommentResponse> comments
    ) {
        public static Detail from(Post post) {
            return new Detail(
                    post.getId(),
                    post.getTitle(),
                    post.getContent(),
                    post.getMember().getNickname(),
                    post.getCategory(),
                    post.getViewCount(),
                    post.getLikeCount(),
                    post.getCreatedAt(),
                    post.getModifiedAt(),
                    post.getCommentList().stream()
                            .map(CommentResponse::from)
                            .toList()
            );
        }
    }
}
