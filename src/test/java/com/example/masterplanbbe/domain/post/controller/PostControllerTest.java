package com.example.masterplanbbe.domain.post.controller;

import com.example.masterplanbbe.presentation.request.PostRequest;
import com.example.masterplanbbe.presentation.response.PostResponse;
import com.example.masterplanbbe.domain.enums.Category;
import com.example.masterplanbbe.application.service.PostService;
import com.example.masterplanbbe.presentation.controller.PostController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostController.class)
@MockBean(JpaMetamodelMappingContext.class)
@DisplayName("게시글 컨트롤러 테스트")
class PostControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PostService postService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("게시글 생성 성공")
    @WithMockUser(username = "test@example.com")
    void createPost() throws Exception {
        // given
        String email = "test@example.com";

        PostRequest requestDTO = new PostRequest(Category.TIP, "Test Title", "Test Content");
        PostResponse.Summary responseDTO = new PostResponse.Summary(
                1L, "Test Title", "Test Content", "Test Nickname", null, Category.TIP, 0, 0, 0);

        when(postService.createPost(eq(email), any(PostRequest.class))).thenReturn(responseDTO);

        // when & then
        mvc.perform(post("/api/v1/posts")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.title").value("Test Title"))
                .andExpect(jsonPath("$.data.content").value("Test Content"));
    }

    @Test
    @DisplayName("특정 게시글 조회")
    @WithMockUser(username = "test@example.com")
    void getPost() throws Exception {
        // given
        PostResponse.Detail responseDTO = new PostResponse.Detail(
                1L, "Test Title", "Test Content", "Test Nickname", Category.TIP, 0, 0, null, null, null);

        when(postService.getPost(anyLong())).thenReturn(responseDTO);

        // when & then
        mvc.perform(get("/api/v1/posts/{postId}", 1L)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.title").value("Test Title"))
                .andExpect(jsonPath("$.data.content").value("Test Content"));
    }

    @Test
    @DisplayName("모든 게시글 조회")
    @WithMockUser(username = "test@example.com")
    void getAllPost() throws Exception {
        // given
        PostResponse.Summary responseDTO = new PostResponse.Summary(
                1L, "Test Title", "Test Content", "Test Nickname", null, Category.TIP, 0, 0, null);

        Pageable pageable = PageRequest.of(0, 10);
        Page<PostResponse.Summary> responsePage = new PageImpl<>(List.of(responseDTO), pageable, 1);

        when(postService.getAllPost(any(Pageable.class))).thenReturn(responsePage);

        // when & then
        mvc.perform(get("/api/v1/posts")
                        .with(csrf())
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content[0].title").value("Test Title"))
                .andExpect(jsonPath("$.data.content[0].content").value("Test Content"))
                .andExpect(jsonPath("$.data.content[0].nickname").value("Test Nickname"))
                .andExpect(jsonPath("$.data.totalElements").value(1))
                .andExpect(jsonPath("$.data.totalPages").value(1));
    }

    @Test
    @DisplayName("게시글 수정")
    @WithMockUser(username = "test@example.com")
    void updatePost() throws Exception {
        // given
        String email = "test@example.com";

        PostRequest requestDTO = new PostRequest(Category.TIP, "Updated Title", "Updated Content");
        PostResponse.Detail responseDTO = new PostResponse.Detail(
                1L, "Updated Title", "Updated Content", "Test Nickname", Category.TIP, 0, 0, null, null, null);

        when(postService.updatePost(anyLong(), eq(email), any(PostRequest.class))).thenReturn(responseDTO);

        // when & then
        mvc.perform(patch("/api/v1/posts/{postId}", 1L)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.title").value("Updated Title"))
                .andExpect(jsonPath("$.data.content").value("Updated Content"))
                .andExpect(jsonPath("$.data.nickname").value("Test Nickname"));
    }

    @Test
    @DisplayName("게시글 삭제")
    @WithMockUser(username = "test@example.com")
    void deletePost() throws Exception {
        // given
        String email = "test@example.com";

        // when & then
        mvc.perform(delete("/api/v1/posts/{postId}", 1L)
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(postService).deletePost(1L, email);
    }

    @Test
    @DisplayName("내가 작성한 글 조회")
    @WithMockUser(username = "test@example.com")
    void getMyPost() throws Exception {
        // given
        String email = "test@example.com";

        PostResponse.Summary responseDTO = new PostResponse.Summary(
                1L, "Test Title", "Test Content", "Test Nickname", null, Category.TIP, 0, 0, null);

        Pageable pageable = PageRequest.of(0, 10);
        Page<PostResponse.Summary> responsePage = new PageImpl<>(List.of(responseDTO), pageable, 1);

        when(postService.getMyPost(eq(email), any(Pageable.class))).thenReturn(responsePage);

        // when & then
        mvc.perform(get("/api/v1/posts/my")
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content[0].title").value("Test Title"))
                .andExpect(jsonPath("$.data.content[0].content").value("Test Content"))
                .andExpect(jsonPath("$.data.content[0].nickname").value("Test Nickname"))
                .andExpect(jsonPath("$.data.totalElements").value(1))
                .andExpect(jsonPath("$.data.totalPages").value(1));
    }
}