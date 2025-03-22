package com.example.masterplanbbe.domain.repository;

import com.example.masterplanbbe.domain.entity.Spec;
import com.example.masterplanbbe.domain.entity.SpecReview;
import com.example.masterplanbbe.domain.enums.ExamType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SpecReviewRepository extends JpaRepository<SpecReview, Long> {

    Page<SpecReview> findBySpec(Spec spec, Pageable pageable);

    SpecReview findByIdAndSpecId(Long id, Long specId);

    boolean existsBySpecIdAndMemberId(Long specId, Long memberId);

    boolean existsBySpecIdAndMemberEmail(Long specId, String email);

}
