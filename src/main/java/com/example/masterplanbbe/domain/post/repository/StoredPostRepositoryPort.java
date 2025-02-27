package com.example.masterplanbbe.domain.post.repository;

import com.example.masterplanbbe.domain.post.entity.Post;
import com.example.masterplanbbe.domain.post.entity.StoredPost;
import com.example.masterplanbbe.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StoredPostRepositoryPort {

    boolean existsByMemberAndPost(Member member, Post post);

    void deleteByMemberAndPost(Member member, Post post);

    Page<StoredPost> findByMemberId(Long memberId, Pageable pageable);

    void save(StoredPost storedPost);
}