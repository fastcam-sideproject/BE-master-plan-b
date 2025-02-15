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

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();

    @Builder
    public Post(String title, String content, Member member) {
        this.title = title;
        this.content = content;
        this.member = member;
        this.likeCount = 0; // ✅ 기본값 설정
    }

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

    public void addLike(){
        if (this.likeCount == null) {
            this.likeCount = 0; //
        }
        this.likeCount += 1;
    }

    public void deleteLike(){
        this.likeCount -= 1;
    }
}
