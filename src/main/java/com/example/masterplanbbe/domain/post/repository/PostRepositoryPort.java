package com.example.masterplanbbe.domain.post.repository;

import com.example.masterplanbbe.domain.post.entity.Post;

import java.util.List;

public interface PostRepositoryPort {
    Post findById(Long id);

    Post save(Post post);

    List<Post> findAll();

    void delete(Long id);
}
