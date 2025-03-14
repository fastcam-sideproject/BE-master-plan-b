package com.example.masterplanbbe.presentation.request;

import com.example.masterplanbbe.domain.enums.Category;
import com.example.masterplanbbe.domain.entity.Post;
import com.example.masterplanbbe.domain.entity.Member;
import lombok.Builder;

@Builder
public record PostRequest(
        Category category,
        String title,
        String content
) {
    public Post toEntity(Member member) {
        return Post.builder()
                .title(title)
                .content(content)
                .category(category)
                .member(member)
                .build();
    }
}
