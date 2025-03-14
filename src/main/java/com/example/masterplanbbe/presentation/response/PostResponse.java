package com.example.masterplanbbe.presentation.response;

import com.example.masterplanbbe.domain.enums.Category;
import com.example.masterplanbbe.domain.entity.Post;

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
                    post.getLikeCount(),
                    post.getViewCount(),
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
