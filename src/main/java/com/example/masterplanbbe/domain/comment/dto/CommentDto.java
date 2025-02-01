package com.example.masterplanbbe.domain.comment.dto;


import com.example.masterplanbbe.domain.comment.entity.Comment;
import com.example.masterplanbbe.domain.post.entity.Post;
import com.example.masterplanbbe.member.entity.Member;
import lombok.*;

public class CommentDto {

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class CommentRequestDto {
        private String content;
        private Long memberId;
        private Long postId;

        public Comment toEntity(Post post, Member member) {
            return Comment.builder()
                    .content(content)
                    .member(member)
                    .post(post)
                    .build();
        }
    }

    @Getter
    @AllArgsConstructor
    public static class CommentResponseDto {
        private String content;
        private String nickname;
        private Long postId;

        public CommentResponseDto(Comment comment) {
            this.content = comment.getContent();
            this.nickname = comment.getMember().getUserId();
            this.postId = comment.getPost().getId();
        }
    }

}
