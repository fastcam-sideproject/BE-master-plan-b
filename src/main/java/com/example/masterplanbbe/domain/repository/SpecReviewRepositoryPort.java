package com.example.masterplanbbe.domain.repository;

import com.example.masterplanbbe.domain.spec.entity.Spec;
import com.example.masterplanbbe.domain.entity.SpecReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SpecReviewRepositoryPort{
    SpecReview findById(Long id);

    void deleteById(Long id);

    SpecReview save(SpecReview specReview);

    Page<SpecReview> findBySpec(Spec spec, Pageable pageable);

    SpecReview findByIdAndSpecId(Long id, Long specId);

    boolean existsBySpecIdAndMemberEmail(Long specId, String email);
}
