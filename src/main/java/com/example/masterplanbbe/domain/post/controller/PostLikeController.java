package com.example.masterplanbbe.domain.post.controller;

import com.example.masterplanbbe.common.response.ApiResponse;
import com.example.masterplanbbe.domain.post.dto.PostResponse;
import com.example.masterplanbbe.domain.post.service.PostLikeService;
import com.example.masterplanbbe.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Like controller api", description = "좋아요 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostLikeController {

    private final PostLikeService postLikeService;

    @Operation(summary = "게시글 좋아요 추가")
    @PostMapping("/{postId}/like")
    public ResponseEntity<ApiResponse<PostResponse.Detail>> addLike(
            @PathVariable Long postId,
            @RequestHeader(value = "memberId") Long memberId
    ) {

        return ResponseEntity.ok()
                .body(ApiResponse.ok(postLikeService.addLike(postId,memberId)));
    }

    @Operation(summary = "내가 좋아요한 게시글 조회")
    @GetMapping("/posts/liked")
    public ResponseEntity<ApiResponse<Page<PostResponse.Summary>>> getLikedPosts(
            @RequestHeader("memberId") Long memberId,
            Pageable pageable
    ) {
        return ResponseEntity.ok()
                .body(ApiResponse.ok(postLikeService.getLikedPosts(memberId,pageable)));
    }
}
