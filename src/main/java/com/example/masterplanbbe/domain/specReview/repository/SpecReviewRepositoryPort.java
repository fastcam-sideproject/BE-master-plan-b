package com.example.masterplanbbe.domain.specReview.repository;

import com.example.masterplanbbe.domain.spec.entity.Spec;
import com.example.masterplanbbe.domain.specReview.entity.SpecReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SpecReviewRepositoryPort{
    SpecReview findById(Long id);

    void deleteById(Long id);

    SpecReview save(SpecReview specReview);

    Page<SpecReview> findBySpec(Spec spec, Pageable pageable);

    SpecReview findByIdAndSpecId(Long id, Long specId);

    boolean existsBySpecIdAndMemberId(Long specId, Long memberId);
}
