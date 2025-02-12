package com.example.masterplanbbe.domain.post.controller;

import com.example.masterplanbbe.common.response.ApiResponse;
import com.example.masterplanbbe.domain.post.dto.PostRequest;
import com.example.masterplanbbe.domain.post.dto.PostResponse;
import com.example.masterplanbbe.domain.post.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Post controller api", description = "게시판 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class PostController {

    private final PostService postService;

    @Operation(summary = "게시글 생성")
    @PostMapping("/posts")
    public ResponseEntity<ApiResponse<PostResponse.Summary>> createPost(
            @RequestBody PostRequest postRequestDTO,
            @RequestHeader(value = "memberId") Long memberId
    ) {
        return ResponseEntity.ok()
                .body(ApiResponse.ok(postService.createPost(memberId, postRequestDTO)));
    }

    @Operation(summary = "특정 게시글 조회")
    @GetMapping("/posts/{postId}")
    public ResponseEntity<ApiResponse<PostResponse.Detail>> getPost(
            @PathVariable Long postId) {
        return ResponseEntity.ok()
                .body(ApiResponse.ok(postService.getPost(postId)));
    }

    @Operation(summary = "전체 게시글 조회")
    @GetMapping("/posts")
    public ResponseEntity<ApiResponse<List<PostResponse.Summary>>> getAllPost() {
        List<PostResponse.Summary> postList = postService.getAllPost();

        return ResponseEntity.ok()
                .body(ApiResponse.ok(postList));
    }

    @Operation(summary = "특정 게시글 수정")
    @PatchMapping("/posts/{postId}")
    public ResponseEntity<ApiResponse<PostResponse.Detail>> updatePost(
            @RequestBody PostRequest postRequestDTO,
            @PathVariable Long postId,
            @RequestHeader(value = "memberId") Long memberId
    ) {
        return ResponseEntity.ok()
                .body(ApiResponse.ok(postService.updatePost(postId, memberId, postRequestDTO)));
    }

    @Operation(summary = "특정 게시글 삭제")
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<ApiResponse<Void>> deletePost(
            @PathVariable Long postId,
            @RequestHeader(value = "memberId") Long memberId
    ) {
        postService.deletePost(postId, memberId);
        return ResponseEntity.ok().body(ApiResponse.ok());
    }
}

