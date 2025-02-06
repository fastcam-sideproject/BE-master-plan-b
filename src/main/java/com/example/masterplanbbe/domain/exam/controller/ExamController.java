package com.example.masterplanbbe.domain.exam.controller;

import com.example.masterplanbbe.common.response.ApiResponse;
import com.example.masterplanbbe.domain.exam.dto.ExamItemCardDto;
import com.example.masterplanbbe.domain.exam.request.ExamCreateRequest;
import com.example.masterplanbbe.domain.exam.request.ExamUpdateRequest;
import com.example.masterplanbbe.domain.exam.response.CreateExamResponse;
import com.example.masterplanbbe.domain.exam.response.ReadExamResponse;
import com.example.masterplanbbe.domain.exam.response.UpdateExamResponse;
import com.example.masterplanbbe.domain.exam.service.ExamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Exam controller api", description = "시험 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/exam")
public class ExamController {
    private final ExamService examService;

    @Operation(summary = "시험 목록 조회")
    @GetMapping
    public ResponseEntity<ApiResponse<Page<ExamItemCardDto>>> getAllExam(
            @PageableDefault Pageable pageable,
            @RequestParam(name = "memberId") String memberId//TODO: security context로 변경
    ) {
        return ResponseEntity.ok()
                .body(ApiResponse.ok(examService.getAllExam(pageable, memberId)));
    }

    @Operation(summary = "시험 상세 조회")
    @GetMapping("/{examId}")
    public ResponseEntity<ApiResponse<ReadExamResponse>> getExam(
            @PathVariable("examId") Long examId
    ) {
        return ResponseEntity.ok()
                .body(ApiResponse.ok(examService.getExam(examId)));
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
