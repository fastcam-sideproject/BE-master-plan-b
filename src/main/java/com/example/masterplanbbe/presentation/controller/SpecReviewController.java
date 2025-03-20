package com.example.masterplanbbe.presentation.controller;

import com.example.masterplanbbe.common.response.ApiResponse;
import com.example.masterplanbbe.presentation.request.SpecReviewRequest;
import com.example.masterplanbbe.presentation.response.SpecReviewResponse;
import com.example.masterplanbbe.domain.service.SpecReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Tag(name = "SpecReview controller api", description = "스펙후기 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/specs/{specId}/reviews")
public class SpecReviewController {

    private final SpecReviewService specReviewService;

    @Operation(summary = "스펙 리뷰 생성")
    @PostMapping("")
    public ResponseEntity<ApiResponse<SpecReviewResponse>> createReview(
            @PathVariable Long specId,
            @RequestBody SpecReviewRequest specReviewRequest,
            Authentication authentication
    ) {
        String email = authentication.getName();

        return ResponseEntity.ok()
                .body(ApiResponse.ok(specReviewService.addReview(specReviewRequest, specId, email)));
    }

    @Operation(summary = "스펙 리뷰 조회")
    @GetMapping("/{reviewId}")
    public ResponseEntity<ApiResponse<SpecReviewResponse>> getReview(
            @PathVariable Long specId,
            @PathVariable Long reviewId
    ) {
        return ResponseEntity.ok()
                .body(ApiResponse.ok(specReviewService.getReview(specId, reviewId)));
    }

    @Operation(summary = "스펙 리뷰 전체조회")
    @GetMapping("")
    public ResponseEntity<ApiResponse<Page<SpecReviewResponse>>> getAllReview(
        @PathVariable Long specId,
        Pageable pageable
    ) {
        return ResponseEntity.ok()
                .body(ApiResponse.ok(specReviewService.getAllReview(specId, pageable)));
    }

    @Operation(summary = "스펙 리뷰 수정")
    @PatchMapping("/{reviewId}")
    public ResponseEntity<ApiResponse<SpecReviewResponse>> updateReview(
            @PathVariable Long specId,
            @PathVariable Long reviewId,
            @RequestBody SpecReviewRequest specReviewRequest,
            Authentication authentication
    ) {
        String email = authentication.getName();

        return ResponseEntity.ok()
                .body(ApiResponse.ok(specReviewService.updateReview(specReviewRequest, email, reviewId, specId)));
    }

    @Operation(summary = "스펙 리뷰 삭제")
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<ApiResponse<Void>> deleteReview(
            @PathVariable Long specId,
            @PathVariable Long reviewId,
            Authentication authentication
    ) {
        String email = authentication.getName();
        specReviewService.deleteReview(specId, reviewId, email);

        return ResponseEntity.ok()
                .body(ApiResponse.ok());
    }


}
