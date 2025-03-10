package com.example.masterplanbbe.domain.post.controller;

import com.example.masterplanbbe.common.response.ApiResponse;
import com.example.masterplanbbe.domain.post.dto.PostResponse;
import com.example.masterplanbbe.domain.post.service.StoredPostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Tag(name = "StoredPost controller api", description = "게시판 북마크 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class StoredPostController {

    private final StoredPostService storedPostService;

    @Operation(summary = "게시글 저장")
    @PostMapping("/{postId}/store")
    public ResponseEntity<ApiResponse<PostResponse.Detail>> addStoredPost(
            @PathVariable Long postId,
            Authentication authentication
    ) {
        String email = authentication.getName();

        return ResponseEntity.ok()
                .body(ApiResponse.ok(storedPostService.toggleStoredPost(email, postId)));
    }

    @Operation(summary = "내가 저장한 게시글 확인")
    @GetMapping("/posts/stored")
    public ResponseEntity<ApiResponse<Page<PostResponse.Summary>>> getStoredPost(
            Authentication authentication,
            Pageable pageable
    ) {
        String email = authentication.getName();

        return ResponseEntity.ok()
                .body(ApiResponse.ok(storedPostService.getStoredPost(email, pageable)));
    }

}
