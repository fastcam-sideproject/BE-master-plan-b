package com.example.masterplanbbe.domain.specReview.repository;

import com.example.masterplanbbe.domain.post.entity.Post;
import com.example.masterplanbbe.domain.spec.entity.Spec;
import com.example.masterplanbbe.domain.specReview.entity.SpecReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpecReviewRepository extends JpaRepository<SpecReview, Long> {

    Page<SpecReview> findBySpec(Spec spec, Pageable pageable);

    SpecReview findByIdAndSpecId(Long id, Long specId);

    boolean existsBySpecIdAndMemberId(Long specId, Long memberId);

    boolean existsBySpecIdAndMemberEmail(Long specId, String email);
}
