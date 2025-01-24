package com.example.masterplanbbe.domain.post.entity;

import com.example.masterplanbbe.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {

    private String title;

    private String content;

    private String nickname;


    public void updatePost(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
