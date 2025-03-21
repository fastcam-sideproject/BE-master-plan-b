package com.example.masterplanbbe.domain.repository;

import com.example.masterplanbbe.domain.entity.Member;
import com.example.masterplanbbe.domain.entity.LikeSpecReview;
import com.example.masterplanbbe.domain.entity.SpecReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeSpecReviewRepository extends JpaRepository<LikeSpecReview, Long> {
    boolean existsByMemberAndSpecReview(Member member, SpecReview specReview);

    void deleteByMemberAndSpecReview(Member member, SpecReview specReview);
}
