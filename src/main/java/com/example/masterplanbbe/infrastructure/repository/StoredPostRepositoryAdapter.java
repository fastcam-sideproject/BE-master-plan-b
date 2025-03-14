package com.example.masterplanbbe.infrastructure.repository;

import com.example.masterplanbbe.domain.entity.Post;
import com.example.masterplanbbe.domain.entity.StoredPost;
import com.example.masterplanbbe.domain.entity.Member;
import com.example.masterplanbbe.domain.repository.StoredPostRepository;
import com.example.masterplanbbe.domain.repository.StoredPostRepositoryPort;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class StoredPostRepositoryAdapter implements StoredPostRepositoryPort {

    private final StoredPostRepository storedPostRepository;

    @Override
    public boolean existsByMemberAndPost(Member member, Post post) {
        return storedPostRepository.existsByMemberAndPost(member, post);
    }

    @Override
    public void deleteByMemberAndPost(Member member, Post post) {
        storedPostRepository.deleteByMemberAndPost(member, post);

    }

    @Override
    public Page<StoredPost> findByMemberId(Long memberId, Pageable pageable) {
        return storedPostRepository.findByMemberId(memberId, pageable);
    }

    @Override
    public void save(StoredPost storedPost) {
        storedPostRepository.save(storedPost);
    }
}
