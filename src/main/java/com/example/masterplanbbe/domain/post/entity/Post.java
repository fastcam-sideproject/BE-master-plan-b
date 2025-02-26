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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Post extends FullAuditEntity {

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Integer likeCount;

    @ManyToOne
    private Member member;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();

    @Builder
    public Post(String title, String content, Member member) {
        this.title = title;
        this.content = content;
        this.member = member;
        this.likeCount = 0; //
    }

    @Builder(builderMethodName = "fullBuilder")
    public Post(String title, String content, Member member, Integer likeCount) {
        this.title = title;
        this.content = content;
        this.member = member;
        this.likeCount = likeCount;
    }

    public void updatePost(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void updateLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }
}
