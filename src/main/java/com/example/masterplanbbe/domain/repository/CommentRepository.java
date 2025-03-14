package com.example.masterplanbbe.domain.repository;

import com.example.masterplanbbe.domain.entity.Comment;
import com.example.masterplanbbe.domain.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);
}
