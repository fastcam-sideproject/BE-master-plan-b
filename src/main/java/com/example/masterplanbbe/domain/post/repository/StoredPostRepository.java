package com.example.masterplanbbe.domain.post.repository;

import com.example.masterplanbbe.domain.post.entity.Post;
import com.example.masterplanbbe.domain.post.entity.StoredPost;
import com.example.masterplanbbe.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoredPostRepository extends JpaRepository<StoredPost, Long> {

    void deleteByMemberAndPost(Member member, Post post);

    boolean existsByMemberAndPost(Member member, Post post);

    Page<StoredPost> findByMemberId(Long memberId, Pageable pageable);
}
