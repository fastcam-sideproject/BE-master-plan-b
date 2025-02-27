package com.example.masterplanbbe.domain.studyLog.controller;

import com.example.masterplanbbe.common.response.ApiResponse;
import com.example.masterplanbbe.domain.studyLog.request.StudyLogRequest;
import com.example.masterplanbbe.domain.studyLog.service.StudyLogService;
import com.example.masterplanbbe.security.user.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "StudyLog controller api", description = "공부기록 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/study-logs")
public class StudyLogController {
    private final StudyLogService studyLogService;

    @Operation(summary = "학습 기록 작성")
    @PostMapping(path = "")
    public ResponseEntity<ApiResponse<?>> create(
            @RequestBody StudyLogRequest studyLogRequest,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return ResponseEntity.ok()
                .body(ApiResponse.ok(studyLogService.create(studyLogRequest, userDetails.getUsername())));
    }

    @Operation(summary = "학습 기록 수정")
    @PatchMapping(path = "/{study-log-id}")
    public ResponseEntity<ApiResponse<?>> update(
            @RequestBody StudyLogRequest studyLogRequest,
            @PathVariable(name = "study-log-id") Long studyLogId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return ResponseEntity.ok()
                .body(ApiResponse.ok(studyLogService.update(studyLogRequest, studyLogId, userDetails.getUsername())));
    }

}
