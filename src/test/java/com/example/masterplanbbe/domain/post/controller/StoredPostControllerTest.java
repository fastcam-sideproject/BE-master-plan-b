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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

@WebMvcTest(StoredPostController.class)
@MockBean(JpaMetamodelMappingContext.class)
@DisplayName("게시글 북마크 컨트롤러 테스트")
public class StoredPostControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private StoredPostService storedPostService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @WithMockUser(username = "test@naver.com")
    @DisplayName("게시글 북마크 추가 API 테스트")
    void addStoredPost() throws Exception {
        // Given
        Long postId = 1L;
        PostResponse.Detail response = new PostResponse.Detail(postId, "Test Title", "Test Content", "testUser", null, 0, 0, null, null, List.of());

        given(storedPostService.toggleStoredPost(any(String.class), any(Long.class)))
                .willReturn(response);

        // When
        ResultActions result = mvc.perform(MockMvcRequestBuilders.post("/api/v1/{postId}/store", postId)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON));

        // Then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.data.postId").value(postId))
                .andExpect(jsonPath("$.data.title").value("Test Title"))
                .andExpect(jsonPath("$.data.content").value("Test Content"))
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "test@naver.com")
    @DisplayName("내가 저장한 게시글 목록 조회 API 테스트")
    void getStoredPost() throws Exception {
        // Given
        Pageable pageable = PageRequest.of(0, 10, Sort.by("createdAt").descending());
        PostResponse.Summary postSummary = new PostResponse.Summary(1L, "Test Title", "Test Content", "testUser", null, null, 0, 0, 0);
        Page<PostResponse.Summary> pageResponse = new PageImpl<>(List.of(postSummary), pageable, 1);

        given(storedPostService.getStoredPost(any(String.class), any(Pageable.class)))
                .willReturn(pageResponse);

        // When
        ResultActions result = mvc.perform(MockMvcRequestBuilders.get("/api/v1/posts/stored")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON));

        // Then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content[0].postId").value(1L))
                .andExpect(jsonPath("$.data.content[0].title").value("Test Title"))
                .andDo(print());
    }
}
