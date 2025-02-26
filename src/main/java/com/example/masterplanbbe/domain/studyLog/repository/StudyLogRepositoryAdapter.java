package com.example.masterplanbbe.domain.studyLog.repository;

import com.example.masterplanbbe.domain.studyLog.entity.StudyLog;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class StudyLogRepositoryAdapter implements StudyLogRepositoryPort {

    private final StudyLogRepository studyLogRepository;

    @Override
    public StudyLog save(StudyLog studyLog) {
        return studyLogRepository.save(studyLog);
    }
}
