package com.example.masterplanbbe.domain.post.controller;

import com.example.masterplanbbe.common.response.ApiResponse;
import com.example.masterplanbbe.domain.post.dto.PostResponse;
import com.example.masterplanbbe.domain.post.service.LikePostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Like controller api", description = "좋아요 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class LikePostController {

    private final LikePostService likePostService;

    @Operation(summary = "게시글 좋아요 추가")
    @PostMapping("/{postId}/like")
    public ResponseEntity<ApiResponse<PostResponse.Detail>> addLike(
            @PathVariable Long postId,
            Authentication authentication
    ) {
        String email = authentication.getName();

        return ResponseEntity.ok()
                .body(ApiResponse.ok(likePostService.addLike(postId,email)));
    }

    @Operation(summary = "내가 좋아요한 게시글 조회")
    @GetMapping("/liked")
    public ResponseEntity<ApiResponse<Page<PostResponse.Summary>>> getLikedPosts(
            Authentication authentication,
            Pageable pageable
    ) {
        String email = authentication.getName();

        return ResponseEntity.ok()
                .body(ApiResponse.ok(likePostService.getLikedPosts(email,pageable)));
    }
}
