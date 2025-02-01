package com.example.masterplanbbe.domain.post.dto;

import com.example.masterplanbbe.domain.comment.dto.CommentDto;
import com.example.masterplanbbe.domain.post.entity.Post;
import com.example.masterplanbbe.member.entity.Member;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

public class PostDto {

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class PostRequestDTO {
        private String title;
        private String content;

        public Post toEntity(Member member) {
            return Post.builder()
                    .title(title)
                    .content(content)
                    .member(member)
                    .build();
        }
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class PostResponseDTO {
        private Long postId;
        private String title;
        private String content;
        private String nickname;
        private LocalDateTime createAt;
        private LocalDateTime modifiedAt;
        private List<CommentDto.CommentResponseDto> comments;

        @Builder
        public PostResponseDTO(Post post) {
            this.title = post.getTitle();
            this.content = post.getContent();
        }
    }
}
