package com.example.masterplanbbe.domain.comment.repository;

import com.example.masterplanbbe.domain.comment.entity.Comment;
import com.example.masterplanbbe.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);
}
