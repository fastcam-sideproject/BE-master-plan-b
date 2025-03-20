package com.example.masterplanbbe.presentation.controller;

import com.example.masterplanbbe.presentation.request.CustomPageRequest;
import com.example.masterplanbbe.presentation.response.ApiResponse;
import com.example.masterplanbbe.presentation.response.PageResponse;
import com.example.masterplanbbe.application.dto.ExamItemCardDto;
import com.example.masterplanbbe.domain.enums.ExamSortOption;
import com.example.masterplanbbe.presentation.request.ExamCreateRequest;
import com.example.masterplanbbe.presentation.request.ExamUpdateRequest;
import com.example.masterplanbbe.presentation.response.CreateExamResponse;
import com.example.masterplanbbe.presentation.response.ReadExamResponse;
import com.example.masterplanbbe.presentation.response.UpdateExamResponse;
import com.example.masterplanbbe.domain.service.ExamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Exam controller api", description = "시험 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/exams")
public class ExamController {
    private final ExamService examService;

    @Operation(summary = "시험 목록 조회")
    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<ExamItemCardDto>>> getAllExam(
            @ModelAttribute CustomPageRequest<ExamSortOption> request,
            Authentication authentication
    ) {
        return ResponseEntity.ok()
                .body(ApiResponse.ok(examService.getAllExam(request, authentication.getName())));
    }

    @Operation(summary = "시험 상세 조회")
    @GetMapping("/{examId}")
    public ResponseEntity<ApiResponse<ReadExamResponse>> getExam(
            @PathVariable("examId") Long examId,
            Authentication authentication
    ) {
        return ResponseEntity.ok()
                .body(ApiResponse.ok(examService.getExam(examId, authentication.getName())));
    }

    @Operation(summary = "시험 등록")
    @PostMapping("")
    public ResponseEntity<ApiResponse<CreateExamResponse>> create(
            @RequestBody ExamCreateRequest request
    ) {
        return ResponseEntity.ok()
                .body(ApiResponse.ok(examService.create(request)));
    }

    @Operation(summary = "시험 수정")
    @PatchMapping("/{examId}")
    public ResponseEntity<ApiResponse<UpdateExamResponse>> update(
            @PathVariable("examId") Long examId,
            @RequestBody ExamUpdateRequest request
    ) {
        return ResponseEntity.ok()
                .body(ApiResponse.ok(examService.update(examId, request)));
    }

    @Operation(summary = "시험 삭제")
    @DeleteMapping("/{examId}")
    public ResponseEntity<ApiResponse<String>> delete(
            @PathVariable("examId") Long examId
    ) {
        examService.delete(examId);
        return ResponseEntity.ok()
                .body(ApiResponse.ok("시험 삭제 성공"));
    }
}
