package com.example.masterplanbbe.domain.post.repository;

import com.example.masterplanbbe.domain.post.dto.PostResponse;
import com.example.masterplanbbe.domain.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT p FROM Post p WHERE p.id IN :ids")
    Page<Post> findByIdIn(@Param("ids") List<Long> ids, Pageable pageable);

    Page<Post> findByTitleContaining(String keyword, Pageable pageable);

    Page<Post> findByMemberId(Long memberId, Pageable pageable);
}
