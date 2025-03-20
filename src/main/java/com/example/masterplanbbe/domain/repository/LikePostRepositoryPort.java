package com.example.masterplanbbe.domain.repository;

import com.example.masterplanbbe.domain.entity.LikePost;
import com.example.masterplanbbe.domain.entity.Post;
import com.example.masterplanbbe.domain.entity.Member;

public interface LikePostRepositoryPort {
    boolean existsByMemberAndPost(Member member, Post post);

    void deleteByMemberAndPost(Member member, Post post);

    void save(LikePost likePost);
}
