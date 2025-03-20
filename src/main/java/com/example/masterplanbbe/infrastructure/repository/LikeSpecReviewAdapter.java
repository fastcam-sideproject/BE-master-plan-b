package com.example.masterplanbbe.infrastructure.repository;


import com.example.masterplanbbe.domain.entity.LikeSpecReview;
import com.example.masterplanbbe.domain.entity.Member;
import com.example.masterplanbbe.domain.entity.SpecReview;
import com.example.masterplanbbe.domain.repository.LikeSpecReviewRepository;
import com.example.masterplanbbe.domain.repository.LikeSpecReviewRepositoryPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class LikeSpecReviewAdapter implements LikeSpecReviewRepositoryPort {

    private final LikeSpecReviewRepository likeSpecReviewRepository;

    @Override
    public boolean existsByMemberAndSpecReview(Member member, SpecReview specReview) {
        return likeSpecReviewRepository.existsByMemberAndSpecReview(member, specReview);
    }

    @Override
    public void deleteByMemberAndSpecReview(Member member, SpecReview specReview) {
        likeSpecReviewRepository.deleteByMemberAndSpecReview(member, specReview);
    }

    @Override
    public void save(LikeSpecReview likeSpecReview) {
        likeSpecReviewRepository.save(likeSpecReview);
    }
}
