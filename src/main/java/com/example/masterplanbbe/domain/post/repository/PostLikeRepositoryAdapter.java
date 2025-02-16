package com.example.masterplanbbe.domain.post.repository;

import com.example.masterplanbbe.domain.post.entity.PostLike;
import com.example.masterplanbbe.domain.post.entity.Post;
import com.example.masterplanbbe.member.entity.Member;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class PostLikeRepositoryAdapter implements PostLikeRepositoryPort {

    private final PostLikeRepository postLikeRepository;

    @Override
    public boolean existsByMemberAndPost(Member member, Post post) {
        return postLikeRepository.existsByMemberAndPost(member, post);
    }

    @Override
    public void deleteByMemberAndPost(Member member, Post post) {
        postLikeRepository.deleteByMemberAndPost(member, post);
    }

    @Override
    public void save(PostLike postLike) {
        postLikeRepository.save(postLike);
    }
}
