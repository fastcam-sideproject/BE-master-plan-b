package com.example.masterplanbbe.domain.post.dto;

import com.example.masterplanbbe.domain.post.entity.Post;
import lombok.*;

public class PostDto {

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class PostRequestDTO {
        private String title;
        private String content;
        private String nickname;

        public Post toEntity() {
            return Post.builder()
                    .title(title)
                    .content(content)
                    .nickname("테스트")
                    .build();
        }
    }

    @Getter
    public static class PostResponseDTO {
        private String title;
        private String content;
        private String nickname;

        @Builder
        public PostResponseDTO(Post post) {
            this.title = post.getTitle();
            this.content = post.getContent();
            this.nickname = post.getNickname();
        }
    }
}
