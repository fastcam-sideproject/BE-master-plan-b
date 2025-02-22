package com.example.masterplanbbe.domain.post.repository;

import com.example.masterplanbbe.domain.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostRepositoryPort {
    Post findById(Long id);

    Post save(Post post);

    Page<Post> findAll(Pageable pageable);

    void delete(Long id);

    Page<Post> findByTitleContaining(String keyword, Pageable pageable);

    Page<Post> findAllByIdIn(List<Long> postIdList, Pageable pageable);
}
