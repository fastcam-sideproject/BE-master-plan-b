package com.example.masterplanbbe.domain.specReview.controller;

import com.example.masterplanbbe.common.response.ApiResponse;
import com.example.masterplanbbe.domain.specReview.dto.SpecReviewRequest;
import com.example.masterplanbbe.domain.specReview.dto.SpecReviewResponse;
import com.example.masterplanbbe.domain.specReview.serivce.SpecReviewService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "SpecReview controller api", description = "스펙후기 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/review")
public class SpecReviewController {

    private final SpecReviewService specReviewService;

    public ResponseEntity<ApiResponse<SpecReviewResponse>> createReview(
            @RequestBody SpecReviewRequest specReviewRequest
    ) {
        return ResponseEntity.ok()
                .body(ApiResponse.ok(specReviewService.addReview(specReviewRequest)));
    }
}
