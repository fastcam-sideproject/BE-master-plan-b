package com.example.masterplanbbe.presentation.controller;

import com.example.masterplanbbe.presentation.response.ApiResponse;
import com.example.masterplanbbe.presentation.request.PostRequest;
import com.example.masterplanbbe.presentation.response.PostResponse;
import com.example.masterplanbbe.application.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Post controller api", description = "게시판 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;

    @Operation(summary = "게시글 생성")
    @PostMapping("")
    public ResponseEntity<ApiResponse<PostResponse.Summary>> createPost(
            @RequestBody PostRequest postRequestDTO,
            Authentication authentication
    ) {
        String email = authentication.getName();

        return ResponseEntity.ok()
                .body(ApiResponse.ok(postService.createPost(email, postRequestDTO)));
    }

    @Operation(summary = "특정 게시글 조회")
    @GetMapping("/{postId}")
    public ResponseEntity<ApiResponse<PostResponse.Detail>> getPost(
            @PathVariable Long postId) {
        return ResponseEntity.ok()
                .body(ApiResponse.ok(postService.getPost(postId)));
    }

    @Operation(summary = "전체 게시글 조회")
    @GetMapping("")
    public ResponseEntity<ApiResponse<Page<PostResponse.Summary>>> getAllPost(
            Pageable pageable
    ) {
        return ResponseEntity.ok()
                .body(ApiResponse.ok(postService.getAllPost(pageable)));
    }

    @Operation(summary = "게시글 검색")
    @GetMapping("/posts/search")
    public ResponseEntity<ApiResponse<Page<PostResponse.Summary>>> searchPost(
            @RequestParam String query,
            Pageable pageable
    ) {
        return ResponseEntity.ok()
                .body(ApiResponse.ok(postService.searchPost(query, pageable)));
    }

    @Operation(summary = "특정 게시글 수정")
    @PatchMapping("/{postId}")
    public ResponseEntity<ApiResponse<PostResponse.Detail>> updatePost(
            @RequestBody PostRequest postRequestDTO,
            @PathVariable Long postId,
            Authentication authentication
    ) {
        String email = authentication.getName();

        return ResponseEntity.ok()
                .body(ApiResponse.ok(postService.updatePost(postId, email, postRequestDTO)));
    }

    @Operation(summary = "특정 게시글 삭제")
    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiResponse<Void>> deletePost(
            @PathVariable Long postId,
            Authentication authentication
    ) {
        String email = authentication.getName();

        postService.deletePost(postId, email);
        return ResponseEntity.ok().body(ApiResponse.ok());
    }

    @Operation(summary = "내가 작성한 글 조회")
    @GetMapping("/my")
    public ResponseEntity<ApiResponse<Page<PostResponse.Summary>>> getMyPost(
            Authentication authentication,
            Pageable pageable
    ) {
        String email = authentication.getName();
        return ResponseEntity.ok()
                .body(ApiResponse.ok(postService.getMyPost(email, pageable)));
    }

}

