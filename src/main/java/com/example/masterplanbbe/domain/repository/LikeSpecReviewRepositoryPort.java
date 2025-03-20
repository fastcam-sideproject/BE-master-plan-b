package com.example.masterplanbbe.domain.repository;

import com.example.masterplanbbe.domain.member.entity.Member;
import com.example.masterplanbbe.domain.entity.LikeSpecReview;
import com.example.masterplanbbe.domain.entity.SpecReview;

public interface LikeSpecReviewRepositoryPort {

    boolean existsByMemberAndSpecReview(Member member, SpecReview specReview);

    void deleteByMemberAndSpecReview(Member member, SpecReview specReview);

    void save(LikeSpecReview likeSpecReview);
}
