package com.example.masterplanbbe.domain.studyLog.repository;

import com.example.masterplanbbe.domain.studyLog.entity.StudyLog;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.example.masterplanbbe.common.exception.ErrorCode.NOT_FOUND_STUDY_LOG;
import static com.example.masterplanbbe.common.exception.GlobalException.NotFoundException;

@Repository
@RequiredArgsConstructor
public class StudyLogRepositoryAdapter implements StudyLogRepositoryPort {

    private final StudyLogRepository studyLogRepository;

    @Override
    public StudyLog save(StudyLog studyLog) {
        return studyLogRepository.save(studyLog);
    }

    @Override
    public StudyLog findByIdAndMemberId(Long id, String memberId) {
        return studyLogRepository.findByIdAndMemberEmail(id, memberId).orElseThrow(() -> new NotFoundException(NOT_FOUND_STUDY_LOG));
    }
}
