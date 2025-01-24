package com.example.masterplanbbe.domain.post.controller;

import com.example.masterplanbbe.domain.post.dto.PostDto;
import com.example.masterplanbbe.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class PostController {

    private final PostService postService;

    @PostMapping("/posts")
    public ResponseEntity<PostDto.PostResponseDTO> creatPost(@RequestBody PostDto.PostRequestDTO postRequestDTO) {
        return ResponseEntity.ok()
                .body(postService.createPost(postRequestDTO));
    }

    @GetMapping("/posts/{postid}")
    public ResponseEntity<PostDto.PostResponseDTO> getPost(@PathVariable Long postid) {
        return ResponseEntity.ok()
                .body(postService.getPost(postid));
    }

    @GetMapping("/posts")
    public ResponseEntity<List<PostDto.PostResponseDTO>> getAllPost() {
        List<PostDto.PostResponseDTO> postList = postService.getAllPost();
        return ResponseEntity.ok()
                .body(postList);
    }

    @PatchMapping("/posts/{postid}")
    public ResponseEntity<PostDto.PostResponseDTO> updatePost(@RequestBody PostDto.PostRequestDTO postRequestDTO, @PathVariable Long postid) {
        return ResponseEntity.ok()
                .body(postService.updatePost(postRequestDTO, postid));
    }

    @DeleteMapping("/posts/{postid}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postid) {
        postService.deletePost(postid);
        return ResponseEntity.noContent()
                .build();
    }
}
