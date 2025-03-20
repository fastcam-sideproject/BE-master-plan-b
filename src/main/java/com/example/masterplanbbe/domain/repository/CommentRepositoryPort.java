package com.example.masterplanbbe.domain.repository;

import com.example.masterplanbbe.domain.entity.Comment;
import com.example.masterplanbbe.domain.entity.Post;

import java.util.List;

public interface CommentRepositoryPort {
    Comment save(Comment comment);

    Comment findById(Long id);

    List<Comment> findByPost(Post post);

    void delete(Comment comment);
}
