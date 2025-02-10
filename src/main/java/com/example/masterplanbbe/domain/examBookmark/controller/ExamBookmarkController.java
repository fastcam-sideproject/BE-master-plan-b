package com.example.masterplanbbe.domain.examBookmark.controller;

import com.example.masterplanbbe.common.response.ApiResponse;
import com.example.masterplanbbe.domain.examBookmark.response.CreateExamBookmarkResponse;
import com.example.masterplanbbe.domain.examBookmark.service.ExamBookmarkService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Exam Bookmark controller api", description = "시험 북마크 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/exam")
public class ExamBookmarkController {
    private final ExamBookmarkService examBookmarkService;

    @PostMapping("/{examId}/bookmark")
    public ResponseEntity<ApiResponse<CreateExamBookmarkResponse>> createExamBookmark(
            @PathVariable("examId") Long examId,
            @RequestParam("memberId") Long memberId
    ) {
        return ResponseEntity.ok()
                .body(ApiResponse.ok(examBookmarkService.createExamBookmark(examId, memberId)));
    }

    @DeleteMapping("/{examId}/bookmark")
    public ResponseEntity<ApiResponse<String>> deleteExamBookmark(
            @PathVariable("examId") Long examId
    ) {
        examBookmarkService.deleteExamBookmark(examId);
        return ResponseEntity.ok()
                .body(ApiResponse.ok("시험 북마크 삭제 성공"));
    }
}
