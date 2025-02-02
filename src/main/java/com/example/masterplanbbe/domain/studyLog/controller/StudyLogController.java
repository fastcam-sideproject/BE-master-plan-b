package com.example.masterplanbbe.domain.studyLog.controller;

import com.example.masterplanbbe.common.response.ApiResponse;
import com.example.masterplanbbe.domain.studyLog.request.CreateStudyLogRequest;
import com.example.masterplanbbe.domain.studyLog.response.CreateStudyLogResponse;
import com.example.masterplanbbe.domain.studyLog.service.StudyLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "StudyLog controller api", description = "공부기록 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/study-log")
public class StudyLogController {
    private final StudyLogService studyLogService;

    @Operation(summary = "공부기록 생성")
    public ResponseEntity<ApiResponse<CreateStudyLogResponse>> createStudyLog(
            @RequestBody CreateStudyLogRequest request
    ) {
        return ResponseEntity.ok()
                .body(ApiResponse.ok(studyLogService.createStudyLog(request)));
    }
}
