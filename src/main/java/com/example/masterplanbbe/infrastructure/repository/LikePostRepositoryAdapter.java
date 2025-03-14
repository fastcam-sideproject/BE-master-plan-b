package com.example.masterplanbbe.infrastructure.repository;

import com.example.masterplanbbe.domain.entity.LikePost;
import com.example.masterplanbbe.domain.entity.Post;
import com.example.masterplanbbe.domain.entity.Member;
import com.example.masterplanbbe.domain.repository.LikePostRepository;
import com.example.masterplanbbe.domain.repository.LikePostRepositoryPort;
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
