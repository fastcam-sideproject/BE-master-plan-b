package com.example.masterplanbbe.domain.post.repository;

import com.example.masterplanbbe.domain.post.entity.Post;

public interface PostRepositoryPort {
    Post findById(Long id);
}
