package com.example.masterplanbbe.domain.post.entity;

import com.example.masterplanbbe.common.domain.FullAuditEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Post extends FullAuditEntity {

    private String title;

    private String content;

    private String nickname;


    public void updatePost(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
