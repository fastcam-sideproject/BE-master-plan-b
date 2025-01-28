package com.example.masterplanbbe.domain.post.repository;

import com.example.masterplanbbe.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
