package com.example.masterplanbbe.studyLog.service;

import com.example.masterplanbbe.studyLog.entity.StudyLog;
import com.example.masterplanbbe.studyLog.repository.StudyLogRepository;
import com.example.masterplanbbe.studyLog.request.CreateStudyLogRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudyLogService {
    private final StudyLogRepository studyLogRepository;

    public StudyLog createStudyLog(CreateStudyLogRequest request) {
        return studyLogRepository.save(request.toEntity());
    }
}
