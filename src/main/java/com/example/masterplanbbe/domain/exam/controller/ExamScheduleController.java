package com.example.masterplanbbe.domain.exam.controller;

import com.example.masterplanbbe.common.response.ApiResponse;
import com.example.masterplanbbe.domain.exam.request.ExamScheduleCreateRequest;
import com.example.masterplanbbe.domain.exam.service.ExamScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Exam schedule controller api", description = "시험 일정 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/exam-schedule")
public class ExamScheduleController {

    private final ExamScheduleService examScheduleService;

    @Operation(summary = "시험 일정 추가")
    @PostMapping(path = "")
    public ResponseEntity<ApiResponse<?>> create(
            @RequestBody ExamScheduleCreateRequest request,
            String memberId     // TODO : security 적용 예정
    ) {
        return ResponseEntity.ok()
                .body(ApiResponse.ok());
    }
}
