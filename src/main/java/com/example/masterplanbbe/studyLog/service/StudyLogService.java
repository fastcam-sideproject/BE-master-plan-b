package com.example.masterplanbbe.studyLog.service;

import com.example.masterplanbbe.studyLog.repository.StudyLogRepository;
import com.example.masterplanbbe.studyLog.request.CreateStudyLogRequest;
import com.example.masterplanbbe.studyLog.response.CreateStudyLogResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudyLogService {
    private final StudyLogRepository studyLogRepository;

    public CreateStudyLogResponse createStudyLog(CreateStudyLogRequest request) {
        return new CreateStudyLogResponse(studyLogRepository.save(request.toEntity()));
    }
}
