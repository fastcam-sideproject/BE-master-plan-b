package com.example.masterplanbbe.domain.userExamSession.repository;

import com.example.masterplanbbe.domain.userExamSession.dto.response.UserExamSessionDetailResponse;
import com.example.masterplanbbe.domain.userExamSession.entity.UserExamSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserExamSessionRepositoryPort {
    UserExamSession save(UserExamSession userExamSession);

    UserExamSession getById(Long id);

    void delete(UserExamSession userExamSession);

    UserExamSession findByIdAndMemberId(Long id, Long memberId);

    UserExamSessionDetailResponse findDetailByIdAndMemberId(Long id, Long memberId);

    Page<UserExamSessionDetailResponse> findDetailsByYearAndMonthAndMemberId(Integer year, Integer month, Long memberId, Pageable pageable);
}
