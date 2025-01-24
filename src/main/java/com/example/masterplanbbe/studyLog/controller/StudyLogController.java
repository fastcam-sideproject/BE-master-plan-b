package com.example.masterplanbbe.studyLog.controller;

import com.example.masterplanbbe.studyLog.service.StudyLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/study-log")
public class StudyLogController {
    private final StudyLogService studyLogService;
}
