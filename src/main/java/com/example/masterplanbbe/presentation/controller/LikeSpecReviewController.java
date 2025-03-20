package com.example.masterplanbbe.presentation.controller;


import com.example.masterplanbbe.presentation.response.SpecReviewResponse;
import com.example.masterplanbbe.application.service.LikeSpecReviewService;
import com.example.masterplanbbe.presentation.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Tag(name = "LikeSpecReview controller api", description = "스펙후기 좋아요 API")
@RequestMapping("api/v1/specs/{specId}/reviews/{reviewId}")
@RestController
@RequiredArgsConstructor
public class LikeSpecReviewController {

    private final LikeSpecReviewService likeSpecReviewService;

    @Operation(summary = "스펙 후기 좋아요")
    @PostMapping("")
    public ResponseEntity<ApiResponse<SpecReviewResponse>> likeReview(
            @PathVariable Long specId,
            @PathVariable Long reviewId,
            Authentication authentication
    ) {
        String email = authentication.getName();

        return ResponseEntity.ok()
                .body(ApiResponse.ok(likeSpecReviewService.addLike(reviewId, email)));
    }
}
