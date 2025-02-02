package com.example.masterplanbbe.domain.studyLog.repository;

import com.example.masterplanbbe.domain.studyLog.entity.StudyLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyLogRepository extends JpaRepository<StudyLog, Long>, StudyLogRepositoryCustom {
}
