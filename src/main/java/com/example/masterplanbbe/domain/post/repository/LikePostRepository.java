package com.example.masterplanbbe.domain.post.repository;

import com.example.masterplanbbe.domain.post.entity.LikePost;
import com.example.masterplanbbe.domain.post.entity.Post;
import com.example.masterplanbbe.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikePostRepository extends JpaRepository<LikePost, Long> {

    boolean existsByMemberAndPost(Member member, Post post);

    void deleteByMemberAndPost(Member member, Post post);
}
