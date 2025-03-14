package com.example.masterplanbbe.domain.repository;

import com.example.masterplanbbe.domain.entity.Post;
import com.example.masterplanbbe.domain.entity.StoredPost;
import com.example.masterplanbbe.domain.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StoredPostRepositoryPort {

    boolean existsByMemberAndPost(Member member, Post post);

    void deleteByMemberAndPost(Member member, Post post);

    Page<StoredPost> findByMemberId(Long memberId, Pageable pageable);

    void save(StoredPost storedPost);
}