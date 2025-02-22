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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
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
@DisplayName("게시글 컨트롤러 테스트")
class PostControllerTest {

    private MockMvc mvc;

    @Mock
    private PostService postService;

    @InjectMocks
    private PostController postController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(postController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    @DisplayName("게시글 생성 성공")
    void createPost() throws Exception {
        // given
        PostRequest requestDTO = new PostRequest("Test Title", "Test Content", 1L); // memberId 추가
        PostResponse.Summary responseDTO = new PostResponse.Summary(
                1L, "Test Title", "Test Content", "Test Nickname", null, 0, 0);

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
        PostResponse.Detail responseDTO = new PostResponse.Detail(
                1L, "Test Title", "Test Content", "Test Nickname", null, null, null,null);

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
        PostResponse.Summary responseDTO = new PostResponse.Summary(
                1L, "Test Title", "Test Content", "Test Nickname", null, 0, 0);

        Pageable pageable = PageRequest.of(0, 10);
        Page<PostResponse.Summary> responsePage = new PageImpl<>(List.of(responseDTO), pageable, 1);

        when(postService.getAllPost(any(Pageable.class))).thenReturn(responsePage);


        // when & then
        mvc.perform(get("/api/v1/posts")
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content[0].title").value("Test Title"))
                .andExpect(jsonPath("$.data.content[0].content").value("Test Content"))
                .andExpect(jsonPath("$.data.content[0].nickname").value("Test Nickname"))
                .andExpect(jsonPath("$.data.totalElements").value(1)) //
                .andExpect(jsonPath("$.data.totalPages").value(1));
    }


    @Test
    @DisplayName("게시글 수정")
    void updatePost() throws Exception {
        // given
        PostRequest requestDTO = new PostRequest("Updated Title", "Updated Content",1L);
        PostResponse.Detail responseDTO = new PostResponse.Detail(
                1L, "Updated Title", "Updated Content", "Test Nickname", null, null, null,null);

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