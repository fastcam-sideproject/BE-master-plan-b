package com.example.masterplanbbe.presentation.controller;

import com.example.masterplanbbe.presentation.response.ApiResponse;
import com.example.masterplanbbe.presentation.response.CreateSpecBookmarkResponse;
import com.example.masterplanbbe.application.service.SpecBookmarkService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Spec Bookmark controller api", description = "스펙 북마크 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/spec")
public class SpecBookmarkController {
    private final SpecBookmarkService specBookmarkService;

    @PostMapping("/{specId}/bookmark")
    public ResponseEntity<ApiResponse<CreateSpecBookmarkResponse>> createExamBookmark(
            @PathVariable("specId") Long specId,
            @RequestParam("memberId") Long memberId
    ) {
        return ResponseEntity.ok()
                .body(ApiResponse.ok(specBookmarkService.createExamBookmark(specId, memberId)));
    }

    @DeleteMapping("/{specId}/bookmark")
    public ResponseEntity<ApiResponse<String>> deleteExamBookmark(
            @PathVariable("specId") Long specId
    ) {
        specBookmarkService.deleteExamBookmark(specId);
        return ResponseEntity.ok()
                .body(ApiResponse.ok("스펙 북마크 삭제 성공"));
    }
}
