package com.example.masterplanbbe.domain.examCertifications.controller;

import com.example.masterplanbbe.common.response.ApiResponse;
import com.example.masterplanbbe.domain.examCertifications.dto.request.ExamCertificationCreateRequest;
import com.example.masterplanbbe.domain.examCertifications.dto.response.ExamCertificationCreateResponse;
import com.example.masterplanbbe.domain.examCertifications.service.AuthService;
import com.example.masterplanbbe.domain.examCertifications.service.ExamCertificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Exam Certification api", description = "시험 인증 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/exam/{examId}/certification")
public class ExamCertificationController {
    private final ExamCertificationService examCertificationService;
    private final AuthService authService;

    @Operation(summary = "시험 인증글 등록")
    @PostMapping
    public ResponseEntity<ApiResponse<ExamCertificationCreateResponse>> create(
            @RequestBody ExamCertificationCreateRequest createRequest,
            @PathVariable Long examId) {
        String userId = authService.getUserId();
        ExamCertificationCreateResponse result = examCertificationService.create(createRequest, examId, userId);

        return ResponseEntity.ok()
                .body(ApiResponse.ok(result));
    }
}
