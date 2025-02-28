package com.example.masterplanbbe.domain.post.repository;

import com.example.masterplanbbe.domain.post.entity.LikePost;
import com.example.masterplanbbe.domain.post.entity.Post;
import com.example.masterplanbbe.domain.member.entity.Member;

public interface LikePostRepositoryPort {
    boolean existsByMemberAndPost(Member member, Post post);

    void deleteByMemberAndPost(Member member, Post post);

    void save(LikePost likePost);
}
