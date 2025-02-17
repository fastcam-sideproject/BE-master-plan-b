package com.example.masterplanbbe.domain.userExamSession.repository;

import com.example.masterplanbbe.domain.userExamSession.dto.response.UserExamSessionDetailResponse;
import com.example.masterplanbbe.domain.userExamSession.entity.UserExamSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserExamSessionRepositoryPort {
    UserExamSession save(UserExamSession userExamSession);

    UserExamSession getById(Long id);

    void delete(UserExamSession userExamSession);

    UserExamSession findByIdAndMemberUserId(Long id, String memberId);

    UserExamSessionDetailResponse findDetailByIdAndMemberId(Long id, String memberId);

    Page<UserExamSessionDetailResponse> findDetailsByYearAndMonthAndMemberId(Integer year, Integer month, String memberId, Pageable pageable);
}
