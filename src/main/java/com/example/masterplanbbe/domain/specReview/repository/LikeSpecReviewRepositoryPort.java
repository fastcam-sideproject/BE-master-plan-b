package com.example.masterplanbbe.domain.specReview.repository;

import com.example.masterplanbbe.domain.member.entity.Member;
import com.example.masterplanbbe.domain.post.entity.LikeSpecReview;
import com.example.masterplanbbe.domain.specReview.entity.SpecReview;

public interface LikeSpecReviewRepositoryPort {

    boolean existsByMemberAndSpecReview(Member member, SpecReview specReview);

    void deleteByMemberAndSpecReview(Member member, SpecReview specReview);

    void save(LikeSpecReview likeSpecReview);
}
