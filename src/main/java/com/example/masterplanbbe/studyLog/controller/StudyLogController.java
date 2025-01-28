package com.example.masterplanbbe.studyLog.controller;

import com.example.masterplanbbe.common.response.ApiResponse;
import com.example.masterplanbbe.studyLog.request.CreateStudyLogRequest;
import com.example.masterplanbbe.studyLog.response.CreateStudyLogResponse;
import com.example.masterplanbbe.studyLog.service.StudyLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/study-log")
public class StudyLogController {
    private final StudyLogService studyLogService;

    public ResponseEntity<ApiResponse<CreateStudyLogResponse>> createStudyLog(
            @RequestBody CreateStudyLogRequest request
    ) {
        return ResponseEntity.ok()
                .body(ApiResponse.ok(studyLogService.createStudyLog(request)));
    }
}
