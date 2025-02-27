package com.example.masterplanbbe.domain.post.repository;

import com.example.masterplanbbe.domain.post.entity.LikePost;
import com.example.masterplanbbe.domain.post.entity.Post;
import com.example.masterplanbbe.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class LikePostRepositoryAdapter implements LikePostRepositoryPort {

    private final LikePostRepository likePostRepository;

    @Override
    public boolean existsByMemberAndPost(Member member, Post post) {
        return likePostRepository.existsByMemberAndPost(member, post);
    }

    @Override
    public void deleteByMemberAndPost(Member member, Post post) {
        likePostRepository.deleteByMemberAndPost(member, post);
    }

    @Override
    public void save(LikePost likePost) {
        likePostRepository.save(likePost);
    }
}
