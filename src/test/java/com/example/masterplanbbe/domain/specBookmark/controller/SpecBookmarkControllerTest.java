package com.example.masterplanbbe.domain.specBookmark.controller;

import com.example.masterplanbbe.common.response.ApiResponse;
import com.example.masterplanbbe.domain.specBookmark.response.CreateSpecBookmarkResponse;
import com.example.masterplanbbe.domain.specBookmark.service.SpecBookmarkService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.example.masterplanbbe.domain.fixture.SpecBookmarkFixture.*;
import static java.nio.charset.StandardCharsets.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("스펙 북마크 컨트롤러 테스트")
@ExtendWith(MockitoExtension.class)
public class SpecBookmarkControllerTest {
    @InjectMocks
    SpecBookmarkController examBookmarkController;

    @Mock
    SpecBookmarkService specBookmarkService;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(examBookmarkController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .build();
    }

    @Test
    @DisplayName("사용자는 스펙을 북마크한다.")
    void create_spec_bookmark() throws Exception {
        Long memberId = 1L;
        Long specId = 1L;
        CreateSpecBookmarkResponse mockedResult = new CreateSpecBookmarkResponse(createExistingSpecBookmarkOf(memberId, specId));
        given(specBookmarkService.createExamBookmark(any(Long.class), any(Long.class))).willReturn(mockedResult);

        ResultActions resultActions = mockMvc.perform(post("/api/v1/spec/{specId}/bookmark", specId)
                .contentType(MediaType.APPLICATION_JSON)
                .param("memberId", memberId.toString())
                .accept(MediaType.APPLICATION_JSON));

        resultActions.andExpectAll(status().isOk(), content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(result -> {
                    String responseContent = result.getResponse().getContentAsString();
                    ApiResponse<CreateSpecBookmarkResponse> response = objectMapper.readValue(responseContent, new TypeReference<>() {
                    });
                    CreateSpecBookmarkResponse data = response.getData();
                    assertAll(
                            () -> assertThat(data.examBookmarkId()).isNotNull(),
                            () -> assertThat(data.memberId()).isEqualTo(memberId),
                            () -> assertThat(data.specId()).isEqualTo(specId)
                    );
                });
    }

    @Test
    @DisplayName("사용자는 스펙 북마크를 삭제한다.")
    void delete_spec_bookmark() throws Exception {
        Long specId = 1L;
        doNothing().when(specBookmarkService).deleteExamBookmark(specId);

        ResultActions resultActions = mockMvc.perform(delete("/api/v1/spec/{specId}/bookmark", specId)
                .characterEncoding(UTF_8));

        resultActions.andExpectAll(status().isOk(), content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(result -> {
                    String responseContent = result.getResponse().getContentAsString(UTF_8);
                    ApiResponse<String> response = objectMapper.readValue(responseContent, new TypeReference<>() {
                    });
                    assertThat(response.getData()).isEqualTo("스펙 북마크 삭제 성공");
                });
    }
}
