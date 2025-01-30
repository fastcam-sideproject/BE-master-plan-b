package com.example.masterplanbbe.exam.controller;

import com.example.masterplanbbe.common.response.ApiResponse;
import com.example.masterplanbbe.exam.dto.ExamItemCardDto;
import com.example.masterplanbbe.exam.response.ReadAllExamResponse;
import com.example.masterplanbbe.exam.response.ReadExamResponse;
import com.example.masterplanbbe.exam.service.ExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/exam")
public class ExamController {
    private final ExamService examService;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<ExamItemCardDto>>> getAllExam(
            @PageableDefault Pageable pageable,
            Long memberId //TODO: security context로 변경
    ) {
        return ResponseEntity.ok()
                .body(ApiResponse.ok(examService.getAllExam(pageable, memberId)));
    }

    @GetMapping("/{examId}")
    public ResponseEntity<ApiResponse<ReadExamResponse>> getExam(
            @PathVariable Long examId
    ) {
        return ResponseEntity.ok()
                .body(ApiResponse.ok(examService.getExam(examId)));
    }
}
