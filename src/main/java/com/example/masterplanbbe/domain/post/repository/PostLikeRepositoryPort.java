package com.example.masterplanbbe.domain.post.repository;

import com.example.masterplanbbe.domain.post.entity.PostLike;
import com.example.masterplanbbe.domain.post.entity.Post;
import com.example.masterplanbbe.member.entity.Member;

public interface LikeRepositoryPort {
    boolean existsByMemberAndPost(Member member, Post post);

    void deleteByMemberAndPost(Member member, Post post);

    void save(PostLike postLike);
}
