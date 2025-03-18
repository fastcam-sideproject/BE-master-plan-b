package com.example.masterplanbbe.domain.repository;

import com.example.masterplanbbe.domain.entity.StudyLog;
import com.example.masterplanbbe.presentation.response.StudyLogResponse;

import java.util.List;

public interface StudyLogRepositoryPort {
    // JpaRepository 가 생성하지 않는, 커스텀 메소드를 정의하는 인터페이스
    StudyLog save(StudyLog studyLog);

    StudyLog findByIdAndMemberId(Long id, String memberId);

    List<StudyLogResponse> findAllStudyLogByYearAndMonthAndMemberId(Integer year, Integer month, String memberId);
}
