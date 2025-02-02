package com.example.masterplanbbe.domain.comment.dto;


import com.example.masterplanbbe.domain.comment.entity.Comment;
import com.example.masterplanbbe.domain.post.entity.Post;
import com.example.masterplanbbe.member.entity.Member;
import lombok.*;

import java.time.LocalDateTime;

public class CommentDto {

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class CommentRequestDto {
        private Long postId;
        private Long memberId;
        private String content;

        public Comment toEntity(Post post, Member member) {
            return Comment.builder()
                    .content(content)
                    .member(member)
                    .post(post)
                    .build();
        }
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class CommentResponseDto {
        private Long commentId;
        private String content;
        private String nickname;
        private LocalDateTime createAt;
        private LocalDateTime modifiedAt;

        public CommentResponseDto(Comment comment) {
            this.commentId = comment.getId();
            this.nickname = comment.getMember().getNickname();
            this.content = comment.getContent();
            this.createAt = comment.getCreatedAt();
            this.modifiedAt = comment.getModifiedAt();
        }
    }

}
