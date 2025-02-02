package com.example.masterplanbbe.domain.comment.controller;

import com.example.masterplanbbe.common.response.ApiResponse;
import com.example.masterplanbbe.domain.comment.dto.CommentDto;
import com.example.masterplanbbe.domain.comment.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ApiResponse<List<CommentDto.CommentResponseDto>>> getComments(
            @PathVariable Long postId
    ) {
        return ResponseEntity.ok()
                .body(ApiResponse.ok(commentService.findAllComment(postId)));
    }

    @Operation(summary = "댓글 생성")
    @PostMapping("")
    public ResponseEntity<ApiResponse<CommentDto.CommentResponseDto>> createComment(
            @PathVariable Long postId,
            @RequestHeader(value = "memberId") Long memberId,
            @RequestBody CommentDto.CommentRequestDto commentRequestDto
    ) {
        return ResponseEntity.ok()
                .body(ApiResponse.ok(commentService.createComment(postId, memberId, commentRequestDto)));
    }

    @Operation(summary = "댓글 수정")
    @PostMapping("/{commentId}")
    public ResponseEntity<ApiResponse<CommentDto.CommentResponseDto>> updateComment(
            @PathVariable Long commentId,
            @RequestHeader(value = "memberId") Long memberId,
            @RequestBody CommentDto.CommentRequestDto commentRequestDto
    ) {
        return ResponseEntity.ok()
                .body(ApiResponse.ok(commentService.updateComment(commentId, memberId, commentRequestDto)));
    }

    @Operation(summary = "댓글 삭제")
    @DeleteMapping("/{commentId}")
    public ResponseEntity<ApiResponse<Void>> deleteComment(
            @PathVariable Long postId,
            @PathVariable Long commentId,
            @RequestHeader(value = "memberId") Long memberId
    ) {
        commentService.deleteComment(postId, commentId, memberId);
        return ResponseEntity.ok().body(ApiResponse.ok());
    }
}
