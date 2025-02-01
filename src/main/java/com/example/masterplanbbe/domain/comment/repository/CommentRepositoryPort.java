package com.example.masterplanbbe.domain.comment.repository;

import com.example.masterplanbbe.domain.comment.entity.Comment;
import com.example.masterplanbbe.domain.post.entity.Post;

import java.util.List;

public interface CommentRepositoryPort {
    Comment save(Comment comment);

    Comment findById(Long id);

    List<Comment> findByPost(Post post);

    void delete(Comment comment);
}
