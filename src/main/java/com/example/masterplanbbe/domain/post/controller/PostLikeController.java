package com.example.masterplanbbe.domain.post.controller;

import com.example.masterplanbbe.common.response.ApiResponse;
import com.example.masterplanbbe.domain.post.dto.PostResponse;
import com.example.masterplanbbe.domain.post.service.LikeService;
import com.example.masterplanbbe.member.service.MemberService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Like controller api", description = "좋아요 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class LikeController {

    private final LikeService likeService;
    private final MemberService memberService;

    @PostMapping("/{postId}/like")
    public ResponseEntity<ApiResponse<PostResponse.Detail>> addLike(
            @PathVariable Long postId,
            @RequestHeader(value = "memberId") Long memberId
    ) {

        return ResponseEntity.ok()
                .body(ApiResponse.ok(likeService.addLike(postId,memberId)));
    }
}
