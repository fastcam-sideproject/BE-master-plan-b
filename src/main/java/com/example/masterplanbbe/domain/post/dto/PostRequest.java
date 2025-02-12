package com.example.masterplanbbe.domain.post.dto;

import com.example.masterplanbbe.domain.post.entity.Post;
import com.example.masterplanbbe.member.entity.Member;
import lombok.Builder;

@Builder
public record PostRequest(
        String title,
        String content,
        Long memberId
) {
    public Post toEntity(Member member) {
        return Post.builder()
                .title(title)
                .content(content)
                .member(member)
                .build();
    }
}
