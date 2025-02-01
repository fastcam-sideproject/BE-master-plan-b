package com.example.masterplanbbe.domain.comment.controller;

import com.example.masterplanbbe.common.response.ApiResponse;
import com.example.masterplanbbe.domain.comment.dto.CommentDto;
import com.example.masterplanbbe.domain.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts/{postId}/comment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("")
    public ResponseEntity<ApiResponse<CommentDto.CommentResponseDto>> createComment(
            @PathVariable Long postId,
            @RequestBody CommentDto.CommentRequestDto commentRequestDto
    ) {
        return ResponseEntity.ok()
                .body(ApiResponse.ok(commentService.createComment(postId, 1L, commentRequestDto)));
    }

    @PostMapping("/{commentId}")
    public ResponseEntity<ApiResponse<CommentDto.CommentResponseDto>> updateComment(
            @PathVariable Long commentId,
            @RequestBody CommentDto.CommentRequestDto commentRequestDto
    ) {
        return ResponseEntity.ok()
                .body(ApiResponse.ok(commentService.updateComment(commentId, 1L, commentRequestDto)));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<ApiResponse<Void>> deleteComment(
            @PathVariable Long postId,
            @PathVariable Long commentId,
            Long memberId
    ) {
        commentService.deleteComment(postId, commentId, memberId);
        return ResponseEntity.ok().body(ApiResponse.ok());
    }
}
