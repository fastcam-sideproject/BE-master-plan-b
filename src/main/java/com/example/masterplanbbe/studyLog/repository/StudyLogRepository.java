package com.example.masterplanbbe.studyLog.repository;

import com.example.masterplanbbe.studyLog.entity.StudyLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyLogRepository extends JpaRepository<StudyLog, Long>, StudyLogRepositoryCustom {
}
