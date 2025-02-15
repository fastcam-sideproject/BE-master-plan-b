package com.example.masterplanbbe.domain.post.controller;

import com.example.masterplanbbe.common.response.ApiResponse;
import com.example.masterplanbbe.domain.post.dto.PostResponse;
import com.example.masterplanbbe.domain.post.service.PostLikeService;
import com.example.masterplanbbe.member.service.MemberService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Like controller api", description = "좋아요 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostLikeController {

    private final PostLikeService postLikeService;
    private final MemberService memberService;

    @PostMapping("/{postId}/like")
    public ResponseEntity<ApiResponse<PostResponse.Detail>> addLike(
            @PathVariable Long postId,
            @RequestHeader(value = "memberId") Long memberId
    ) {

        return ResponseEntity.ok()
                .body(ApiResponse.ok(postLikeService.addLike(postId,memberId)));
    }
}
