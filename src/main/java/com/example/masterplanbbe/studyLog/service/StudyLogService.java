package com.example.masterplanbbe.studyLog.service;

import com.example.masterplanbbe.studyLog.repository.StudyLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudyLogService {
    private final StudyLogRepository studyLogRepository;
}
