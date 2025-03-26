package com.example.masterplanbbe.domain.repository;

import com.example.masterplanbbe.domain.entity.SpecReview;
import com.example.masterplanbbe.domain.enums.ExamType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SpecReviewRepository extends JpaRepository<SpecReview, Long> {

    boolean existsByMemberSpecIdAndMemberEmail(Long specId, String email);

    @Query("SELECT sr FROM SpecReview sr WHERE sr.memberSpec.spec.id = :specId")
    Page<SpecReview> findBySpecId(@Param("specId") Long specId, Pageable pageable);

    @Query("SELECT sr FROM SpecReview sr WHERE sr.id = :id AND sr.memberSpec.spec.id = :specId")
    SpecReview findByIdAndSpecId(@Param("id") Long id, @Param("specId") Long specId);

    boolean existsByMemberSpecIdAndMemberEmailAndExamType(Long memberSpecId, String email, ExamType examType);
}
