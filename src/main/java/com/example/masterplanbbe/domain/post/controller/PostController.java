package com.example.masterplanbbe.domain.post.controller;

import com.example.masterplanbbe.common.response.ApiResponse;
import com.example.masterplanbbe.domain.post.dto.PostDto;
import com.example.masterplanbbe.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class PostController {

    private final PostService postService;

    @PostMapping("/posts")
    public ResponseEntity<ApiResponse<PostDto.PostResponseDTO>> createPost(@RequestBody PostDto.PostRequestDTO postRequestDTO) {
        return ResponseEntity.ok()
                .body(ApiResponse.ok(postService.createPost(postRequestDTO)));
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<ApiResponse<PostDto.PostResponseDTO>> getPost(@PathVariable Long postId) {
        return ResponseEntity.ok()
                .body(ApiResponse.ok(postService.getPost(postId)));
    }

    @GetMapping("/posts")
    public ResponseEntity<ApiResponse<List<PostDto.PostResponseDTO>>> getAllPost() {
        List<PostDto.PostResponseDTO> postList = postService.getAllPost();
        return ResponseEntity.ok()
                .body(ApiResponse.ok(postList));
    }

    @PatchMapping("/posts/{postId}")
    public ResponseEntity<ApiResponse<PostDto.PostResponseDTO>> updatePost(@RequestBody PostDto.PostRequestDTO postRequestDTO, @PathVariable Long postId) {
        return ResponseEntity.ok()
                .body(ApiResponse.ok(postService.updatePost(postRequestDTO, postId)));
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<ApiResponse<Void>> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return ResponseEntity.ok().body(ApiResponse.ok());
    }
}

