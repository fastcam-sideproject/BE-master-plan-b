package com.example.masterplanbbe.domain.specReview.repository;

import com.example.masterplanbbe.domain.member.entity.Member;
import com.example.masterplanbbe.domain.post.entity.LikeSpecReview;
import com.example.masterplanbbe.domain.post.entity.Post;
import com.example.masterplanbbe.domain.specReview.entity.SpecReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeSpecReviewRepository extends JpaRepository<LikeSpecReview, Long> {
    boolean existsByMemberAndSpecReview(Member member, SpecReview specReview);

    void deleteByMemberAndSpecReview(Member member, SpecReview specReview);
}
