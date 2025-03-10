package com.example.masterplanbbe.domain.comment.controller;

import com.example.masterplanbbe.common.response.ApiResponse;
import com.example.masterplanbbe.domain.comment.dto.CommentRequest;
import com.example.masterplanbbe.domain.comment.dto.CommentResponse;
import com.example.masterplanbbe.domain.comment.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Comment controller api", description = "댓글 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts/{postId}/comment")
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "댓글 조회")
    @GetMapping("")
    public ResponseEntity<ApiResponse<List<CommentResponse>>> getComments(
            @PathVariable Long postId
    ) {
        return ResponseEntity.ok()
                .body(ApiResponse.ok(commentService.findAllComment(postId)));
    }

    @Operation(summary = "댓글 생성")
    @PostMapping("")
    public ResponseEntity<ApiResponse<CommentResponse>> createComment(
            @PathVariable Long postId,
            Authentication authentication,
            @RequestBody CommentRequest commentRequestDto
    ) {
        String email = authentication.getName();
        return ResponseEntity.ok()
                .body(ApiResponse.ok(commentService.createComment(postId, email, commentRequestDto)));
    }

    @Operation(summary = "댓글 수정")
    @PostMapping("/{commentId}")
    public ResponseEntity<ApiResponse<CommentResponse>> updateComment(
            @PathVariable Long commentId,
            Authentication authentication,
            @RequestBody CommentRequest commentRequestDto
    ) {
        String email = authentication.getName();
        return ResponseEntity.ok()
                .body(ApiResponse.ok(commentService.updateComment(commentId, email, commentRequestDto)));
    }

    @Operation(summary = "댓글 삭제")
    @DeleteMapping("/{commentId}")
    public ResponseEntity<ApiResponse<Void>> deleteComment(
            @PathVariable Long postId,
            @PathVariable Long commentId,
            Authentication authentication
    ) {
        String email = authentication.getName();
        commentService.deleteComment(postId, commentId, email);
        return ResponseEntity.ok().body(ApiResponse.ok());
    }
}
