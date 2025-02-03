package com.example.masterplanbbe.domain.exam.controller;

import com.example.masterplanbbe.common.jackson.RestPage;
import com.example.masterplanbbe.common.response.ApiResponse;
import com.example.masterplanbbe.domain.exam.dto.ExamItemCardDto;
import com.example.masterplanbbe.domain.exam.entity.Exam;
import com.example.masterplanbbe.domain.exam.entity.ExamBookmark;
import com.example.masterplanbbe.domain.exam.request.ExamCreateRequest;
import com.example.masterplanbbe.domain.exam.response.CreateExamResponse;
import com.example.masterplanbbe.domain.exam.service.ExamService;
import com.example.masterplanbbe.domain.fixture.ExamFixture;
import com.example.masterplanbbe.member.entity.Member;
import com.fasterxml.jackson.core.type.TypeReference;
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
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.example.masterplanbbe.domain.fixture.ExamFixture.createExam;
import static com.example.masterplanbbe.domain.fixture.MemberFixture.createMember;
import static java.nio.charset.StandardCharsets.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.util.ReflectionTestUtils.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("시험 컨트롤러 테스트")
@ExtendWith(MockitoExtension.class)
public class ExamControllerTest {
    private MockMvc mockMvc;

    @Mock
    private ExamService examService;

    @InjectMocks
    private ExamController examController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(examController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .build();
    }

    //TODO: mocking 중의 영향으로 Sort 가 반영돼있지 않음
    @Test
    @DisplayName("사용자는 시험 목록을 조회한다.")
    void getAllExam() throws Exception {
        Member member = createMember();
        PageRequest pageRequest = PageRequest.of(0, 5);
        Page<ExamItemCardDto> mockedExamItemCardPage = createMockedExamItemCardPage(member);
        given(examService.getAllExam(any(Pageable.class), eq(member.getUserId()))).willReturn(mockedExamItemCardPage);

        ResultActions resultActions = mockMvc.perform(get("/api/v1/exam")
                .param("page", pageRequest.getPageNumber() + "")
                .param("size", pageRequest.getPageSize() + "")
                .param("sort", "createdAt,desc")
                .param("memberId", member.getUserId())
                .accept(MediaType.APPLICATION_JSON));

        resultActions.andExpectAll(status().isOk(), content().contentType("application/json"))
                .andDo(print());
        assertExamItemCardResponse(resultActions);
    }

    private Page<ExamItemCardDto> createMockedExamItemCardPage(Member member) {
        Exam exam1 = createExam("exam1");
        Exam exam2 = createExam("exam2");
        ExamBookmark examBookmark = new ExamBookmark(member, exam1);
        return new PageImpl<>(List.of(
                new ExamItemCardDto(exam1, isBookmarked(examBookmark, exam1)),
                new ExamItemCardDto(exam2, isBookmarked(examBookmark, exam2))),
                PageRequest.of(0, 5),
                2);
    }

    private void assertExamItemCardResponse(ResultActions resultActions) throws Exception {
        resultActions.andDo(mvcResult -> {
            String responseContent = mvcResult.getResponse().getContentAsString();
            ApiResponse<RestPage<ExamItemCardDto>> response = objectMapper.readValue(responseContent, new TypeReference<>() {
            });
            List<ExamItemCardDto> content = response.getData().getContent();

            assertThat(content).hasSize(2);
            assertAll(
                    () -> assertThat(content.get(0).title()).isEqualTo("exam1"),
                    () -> assertThat(content.get(0).isBookmarked()).isTrue(),
                    () -> assertThat(content.get(1).title()).isEqualTo("exam2"),
                    () -> assertThat(content.get(1).isBookmarked()).isFalse()
            );
        });
    }

    private boolean isBookmarked(ExamBookmark examBookmark,
                                 Exam exam) {
        return examBookmark.getExam() == exam;
    }

    @Test
    @DisplayName("관리자는 시험을 추가한다.")
    void add_exam() throws Exception {
        ExamCreateRequest request = ExamFixture.createExamCreateRequest("exam1");
        CreateExamResponse mockedResult = new CreateExamResponse(createSavedExamFrom(request));
        given(examService.create(any(ExamCreateRequest.class))).willReturn(mockedResult);

        ResultActions resultActions = mockMvc.perform(post("/api/v1/exam")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(UTF_8)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON));

        resultActions.andExpectAll(status().isOk(), content().contentType("application/json"))
                .andDo(print())
                .andDo(mvcResult -> {
                    String responseContent = mvcResult.getResponse().getContentAsString(UTF_8);
                    ApiResponse<CreateExamResponse> response = objectMapper.readValue(responseContent, new TypeReference<>() {
                    });
                    CreateExamResponse data = response.getData();
                    assertAll(
                            () -> assertThat(data.examId()).isNotNull(),
                            () -> assertThat(data.title()).isEqualTo(request.title()),
                            () -> assertThat(data.category()).isEqualTo(request.category()),
                            () -> assertThat(data.authority()).isEqualTo(request.authority()),
                            () -> assertThat(data.subjects()).isEqualTo(request.subjects())
                    );
                });
    }

    private Exam createSavedExamFrom(ExamCreateRequest request) {
        Exam exam = request.toEntity();
        setField(exam, "id", 1L);
        return exam;
    }
}
