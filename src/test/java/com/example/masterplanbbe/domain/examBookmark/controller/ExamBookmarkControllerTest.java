package com.example.masterplanbbe.domain.examBookmark.controller;

import com.example.masterplanbbe.common.response.ApiResponse;
import com.example.masterplanbbe.domain.exam.entity.Exam;
import com.example.masterplanbbe.domain.examBookmark.entity.ExamBookmark;
import com.example.masterplanbbe.domain.examBookmark.response.CreateExamBookmarkResponse;
import com.example.masterplanbbe.domain.examBookmark.service.ExamBookmarkService;
import com.example.masterplanbbe.domain.fixture.ExamFixture;
import com.example.masterplanbbe.domain.fixture.MemberFixture;
import com.example.masterplanbbe.member.entity.Member;
import com.example.masterplanbbe.utils.TestUtils;
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

import static java.nio.charset.StandardCharsets.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.util.ReflectionTestUtils.setField;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("시험 북마크 컨트롤러 테스트")
@ExtendWith(MockitoExtension.class)
public class ExamBookmarkControllerTest {
    @InjectMocks
    ExamBookmarkController examBookmarkController;

    @Mock
    ExamBookmarkService examBookmarkService;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(examBookmarkController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .build();
    }

    @Test
    @DisplayName("사용자는 시험을 북마크한다.")
    void create_exam_bookmark() throws Exception {
        Long memberId = 1L;
        Long examId = 1L;
        Member member = createExistingMemberFrom(memberId);
        Exam exam = createExistingExamFrom(examId);
        CreateExamBookmarkResponse mockedResult = new CreateExamBookmarkResponse(createExistingExamBookmarkOf(member, exam));
        given(examBookmarkService.createExamBookmark(examId, memberId)).willReturn(mockedResult);

        ResultActions resultActions = mockMvc.perform(post("/api/v1/exam/{examId}/bookmark", examId)
                .contentType(MediaType.APPLICATION_JSON)
                .param("memberId", memberId.toString())
                .accept(MediaType.APPLICATION_JSON));

        resultActions.andExpectAll(status().isOk(), content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(result -> {
                    String responseContent = result.getResponse().getContentAsString();
                    ApiResponse<CreateExamBookmarkResponse> response = objectMapper.readValue(responseContent, new TypeReference<>() {
                    });
                    CreateExamBookmarkResponse data = response.getData();
                    assertAll(
                            () -> assertThat(data.examBookmarkId()).isNotNull(),
                            () -> assertThat(data.memberId()).isEqualTo(memberId),
                            () -> assertThat(data.examId()).isEqualTo(examId)
                    );
                });
    }

    private Member createExistingMemberFrom(Long memberId) {
        return TestUtils.withSetup(MemberFixture::createMember, member -> {
            setField(member, "id", memberId);
        });
    }

    private Exam createExistingExamFrom(Long examId) {
        return TestUtils.withSetup(() -> ExamFixture.createExam("exam1"), exam -> {
            setField(exam, "id", examId);
        });
    }

    private ExamBookmark createExistingExamBookmarkOf(Member member,
                                                      Exam exam) {
        return TestUtils.withSetup(() -> new ExamBookmark(member, exam), examBookmark -> {
            setField(examBookmark, "id", 1L);
        });
    }

    @Test
    @DisplayName("사용자는 시험 북마크를 삭제한다.")
    void delete_exam_bookmark() throws Exception {
        Long examId = 1L;
        doNothing().when(examBookmarkService).deleteExamBookmark(examId);

        ResultActions resultActions = mockMvc.perform(delete("/api/v1/exam/{examId}/bookmark", examId)
                .characterEncoding(UTF_8));

        resultActions.andExpectAll(status().isOk(), content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(result -> {
                    String responseContent = result.getResponse().getContentAsString(UTF_8);
                    ApiResponse<String> response = objectMapper.readValue(responseContent, new TypeReference<>() {
                    });
                    assertThat(response.getData()).isEqualTo("시험 북마크 삭제 성공");
                });
    }
}
