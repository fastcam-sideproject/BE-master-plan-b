package com.example.masterplanbbe.domain.repository;

import com.example.masterplanbbe.domain.entity.Spec;
import com.example.masterplanbbe.domain.entity.SpecReview;
import com.example.masterplanbbe.domain.enums.ExamType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SpecReviewRepositoryPort{
    SpecReview findById(Long id);

    void deleteById(Long id);

    SpecReview save(SpecReview specReview);

    SpecReview findByIdAndSpecId(Long id, Long specId);

    boolean existsBySpecIdAndMemberEmail(Long specId, String email);

    Page<SpecReview> findBySpecId(Long specId, Pageable pageable);

    boolean existsBySpecIdAndMemberEmailAndExamType(Long memberSpecId, String email, ExamType examType);
}
