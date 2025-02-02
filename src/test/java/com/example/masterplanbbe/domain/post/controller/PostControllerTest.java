package com.example.masterplanbbe.domain.post.controller;

import com.example.masterplanbbe.domain.post.dto.PostDto;
import com.example.masterplanbbe.domain.post.entity.Post;
import com.example.masterplanbbe.domain.post.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class PostControllerTest {

    private MockMvc mvc;

    @Mock
    private PostService postService;

    @InjectMocks
    private PostController postController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(postController).build();
    }

    @Test
    @DisplayName("게시글 작성 성공")
    void createPost() throws Exception {
        // given
        PostDto.PostRequestDTO requestDTO = new PostDto.PostRequestDTO("Test Title", "Test Content");
        PostDto.PostResponseDTO mockResponse = new PostDto.PostResponseDTO(
                Post.builder()
                        .title("Test Title")
                        .content("Test Content")
                        .build()
        );
        when(postService.createPost(any(Long.class), any(PostDto.PostRequestDTO.class))).thenReturn(mockResponse);

        // when & then
        mvc.perform(post("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.title").value("Test Title"))
                .andExpect(jsonPath("$.data.content").value("Test Content"))
                .andExpect(jsonPath("$.data.nickname").value("Test Nickname"));
    }

    @Test
    @DisplayName("특정 게시글 조회")
    void getPost() throws Exception {
        // given
        PostDto.PostResponseDTO mockResponse = new PostDto.PostResponseDTO(
                Post.builder()
                        .title("Test Title")
                        .content("Test Content")
                        .build()
        );
        when(postService.getPost(any(Long.class))).thenReturn(mockResponse);

        // when & then
        mvc.perform(get("/api/v1/posts/{postid}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.title").value("Test Title"))
                .andExpect(jsonPath("$.data.content").value("Test Content"))
                .andExpect(jsonPath("$.data.nickname").value("Test Nickname"));

        verify(postService).getPost(1L);
    }

    @Test
    @DisplayName("모든 게시글 조회")
    void getAllPost() throws Exception {
        // given
        PostDto.PostResponseDTO mockResponse = new PostDto.PostResponseDTO(
                Post.builder()
                        .title("Test Title")
                        .content("Test Content")
                        .build()
        );
        when(postService.getAllPost()).thenReturn(List.of(mockResponse));

        // when & then
        mvc.perform(get("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].title").value("Test Title"))
                .andExpect(jsonPath("$.data[0].content").value("Test Content"))
                .andExpect(jsonPath("$.data[0].nickname").value("Test Nickname"));
    }

    @Test
    @DisplayName("게시글 수정")
    void updatePost() throws Exception {
        // given
        PostDto.PostRequestDTO requestDTO = new PostDto.PostRequestDTO("Updated Title", "Updated Content");
        PostDto.PostResponseDTO mockResponse = new PostDto.PostResponseDTO(
                Post.builder()
                        .title("Updated Title")
                        .content("Updated Content")
                        .build()
        );
        when(postService.updatePost(any(Long.class), any(Long.class), any(PostDto.PostRequestDTO.class))).thenReturn(mockResponse);

        // when & then
        mvc.perform(patch("/api/v1/posts/{postid}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.title").value("Updated Title"))
                .andExpect(jsonPath("$.data.content").value("Updated Content"))
                .andExpect(jsonPath("$.data.nickname").value("Test Nickname"));
    }

    @Test
    @DisplayName("게시글 삭제")
    void deletePost() throws Exception {
        // given
        Long postId = 1L;
        Long memberId = 1L;

        // when & then
        mvc.perform(delete("/api/v1/posts/{postid}", postId))
                .andExpect(status().isOk());

        verify(postService).deletePost(postId, memberId);
    }
}
