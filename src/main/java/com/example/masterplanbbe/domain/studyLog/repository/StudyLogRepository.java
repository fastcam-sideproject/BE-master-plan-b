package com.example.masterplanbbe.domain.studyLog.repository;

import com.example.masterplanbbe.domain.studyLog.entity.StudyLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudyLogRepository extends JpaRepository<StudyLog, Long> {

    Optional<StudyLog> findByIdAndMemberEmail(Long id, String memberId);
}
