package com.example.masterplanbbe.domain.post.controller;

import com.example.masterplanbbe.domain.post.dto.PostResponse;
import com.example.masterplanbbe.domain.post.service.StoredPostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@DisplayName("게시글 북마크 컨트롤러 테스트")
public class StoredPostControllerTest {

    private MockMvc mvc;

    @InjectMocks
    private StoredPostController storedPostController;

    @Mock
    private StoredPostService storedPostService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(storedPostController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    @DisplayName("게시글 북마크 추가 API 테스트")
    void addStoredPost() throws Exception {
        // Given
        Long postId = 1L;
        Long memberId = 1L;
        PostResponse.Detail response = new PostResponse.Detail(postId, "Test Title", "Test Content", "testUser", null, 0, 0, null, null, List.of());

        given(storedPostService.toggleStoredPost(any(Long.class), any(Long.class)))
                .willReturn(response);

        // When
        ResultActions result = mvc.perform(MockMvcRequestBuilders.post("/api/v1/{postId}/store", postId)
                .header("memberId", memberId)
                .contentType(MediaType.APPLICATION_JSON));

        // Then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.data.postId").value(postId))
                .andExpect(jsonPath("$.data.title").value("Test Title"))
                .andExpect(jsonPath("$.data.content").value("Test Content"))
                .andDo(print());
    }

    @Test
    @DisplayName("내가 저장한 게시글 목록 조회 API 테스트")
    void getStoredPost() throws Exception {
        // Given
        Long memberId = 1L;
        Pageable pageable = PageRequest.of(0, 10, Sort.by("createdAt").descending());
        PostResponse.Summary postSummary = new PostResponse.Summary(1L, "Test Title", "Test Content", "testUser", null, null, 0, 0, 0);
        Page<PostResponse.Summary> pageResponse = new PageImpl<>(List.of(postSummary), pageable, 1);

        given(storedPostService.getStoredPost(any(Long.class), any(Pageable.class)))
                .willReturn(pageResponse);

        // When
        ResultActions result = mvc.perform(MockMvcRequestBuilders.get("/api/v1/posts/stored")
                .header("memberId", memberId)
                .contentType(MediaType.APPLICATION_JSON));

        // Then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content[0].postId").value(1L))
                .andExpect(jsonPath("$.data.content[0].title").value("Test Title"))
                .andDo(print());
    }
}
