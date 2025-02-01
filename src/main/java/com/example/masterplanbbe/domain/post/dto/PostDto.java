package com.example.masterplanbbe.domain.post.dto;

import com.example.masterplanbbe.domain.post.entity.Post;
import com.example.masterplanbbe.member.entity.Member;
import lombok.*;

public class PostDto {

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class PostRequestDTO {
        private String title;
        private String content;
        private Long memberId;

        public Post toEntity() {
            return Post.builder()
                    .title(title)
                    .content(content)
//                    .member()
                    .build();
        }
    }

    @Getter
    @AllArgsConstructor
    public static class PostResponseDTO {
        private String title;
        private String content;
        private String nickname;

        @Builder
        public PostResponseDTO(Post post) {
            this.title = post.getTitle();
            this.content = post.getContent();
//            this.member = new MemberDto(post.getMember());
        }
    }
}
