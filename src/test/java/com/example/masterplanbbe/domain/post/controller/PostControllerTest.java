package com.example.masterplanbbe.domain.post.controller;

import com.example.masterplanbbe.domain.post.dto.PostRequest;
import com.example.masterplanbbe.domain.post.dto.PostResponse;
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

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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
    @DisplayName("게시글 생성 성공")
    void createPost() throws Exception {
        // given
        PostRequest requestDTO = new PostRequest("Test Title", "Test Content", 1L); // memberId 추가
        PostResponse.Summary responseDTO = PostResponse.Summary.builder()
                .postId(1L)
                .title("Test Title")
                .content("Test Content")
                .nickname("Test Nickname")
                .createdAt(LocalDateTime.now())
                .build();

        when(postService.createPost(anyLong(), any(PostRequest.class))).thenReturn(responseDTO);

        // when & then
        mvc.perform(post("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("memberId", 1L)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.title").value("Test Title"))
                .andExpect(jsonPath("$.data.content").value("Test Content"));
    }

    @Test
    @DisplayName("특정 게시글 조회")
    void getPost() throws Exception {
        // given
        PostResponse.Detail responseDTO = PostResponse.Detail.builder()
                .postId(1L)
                .title("Test Title")
                .content("Test Content")
                .nickname("Test Nickname")
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .comments(List.of())
                .build();

        when(postService.getPost(anyLong())).thenReturn(responseDTO);

        // when & then
        mvc.perform(get("/api/v1/posts/{postId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.title").value("Test Title"))
                .andExpect(jsonPath("$.data.content").value("Test Content"));
    }

    @Test
    @DisplayName("모든 게시글 조회")
    void getAllPost() throws Exception {
        // given
        PostResponse.Summary responseDTO = PostResponse.Summary.builder()
                .postId(1L)
                .title("Test Title")
                .content("Test Content")
                .nickname("Test Nickname")
                .createdAt(null)
                .build();
        when(postService.getAllPost()).thenReturn(List.of(responseDTO));

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
        PostRequest requestDTO = new PostRequest("Updated Title", "Updated Content",1L);
        PostResponse.Detail responseDTO = PostResponse.Detail.builder()
                .postId(1L)
                .title("Updated Title")
                .content("Updated Content")
                .nickname("Test Nickname")
                .createdAt(null)
                .modifiedAt(null)
                .comments(List.of())
                .build();
        when(postService.updatePost(anyLong(), anyLong(), any(PostRequest.class))).thenReturn(responseDTO);

        // when & then
        mvc.perform(patch("/api/v1/posts/{postId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("memberId", 1L)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.title").value("Updated Title"))
                .andExpect(jsonPath("$.data.content").value("Updated Content"))
                .andExpect(jsonPath("$.data.nickname").value("Test Nickname"));
    }

    @Test
    @DisplayName("게시글 삭제")
    void deletePost() throws Exception {
        // when & then
        mvc.perform(delete("/api/v1/posts/{postId}", 1L)
                        .header("memberId", 1L))
                .andExpect(status().isOk());

        verify(postService).deletePost(1L, 1L);
    }
}