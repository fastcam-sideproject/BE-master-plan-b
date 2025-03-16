package com.example.masterplanbbe.domain.repository;

import com.example.masterplanbbe.domain.entity.LikePost;
import com.example.masterplanbbe.domain.entity.Post;
import com.example.masterplanbbe.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikePostRepository extends JpaRepository<LikePost, Long> {

    boolean existsByMemberAndPost(Member member, Post post);

    void deleteByMemberAndPost(Member member, Post post);
}
