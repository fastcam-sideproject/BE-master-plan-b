package com.example.masterplanbbe.domain.post.entity;

import com.example.masterplanbbe.common.domain.FullAuditEntity;
import com.example.masterplanbbe.domain.comment.entity.Comment;
import com.example.masterplanbbe.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Post extends FullAuditEntity {

    private String title;

    private String content;

    @ManyToOne
    private Member member;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();

    public void addComment(Comment comment) {
        this.commentList.add(comment);
        if (comment.getPost() != this) {
            comment.setPost(this);
        }
    }

    public void updatePost(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
