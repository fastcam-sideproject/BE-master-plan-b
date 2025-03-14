package com.example.masterplanbbe.domain.exam.controller;

import com.example.masterplanbbe.common.page.CustomPage;
import com.example.masterplanbbe.common.request.CustomPageRequest;
import com.example.masterplanbbe.common.response.ApiResponse;
import com.example.masterplanbbe.common.response.PageResponse;
import com.example.masterplanbbe.domain.exam.dto.ExamItemCardDto;
import com.example.masterplanbbe.domain.exam.enums.ExamSortOption;
import com.example.masterplanbbe.domain.exam.request.ExamCreateRequest;
import com.example.masterplanbbe.domain.exam.response.CreateExamResponse;
import com.example.masterplanbbe.domain.exam.service.ExamService;
import com.example.masterplanbbe.domain.member.entity.Member;
import com.example.masterplanbbe.domain.spec.entity.Spec;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.example.masterplanbbe.domain.fixture.ExamFixture.*;
import static com.example.masterplanbbe.domain.fixture.MemberFixture.createExistingMember;
import static com.example.masterplanbbe.domain.fixture.SecurityFixture.*;
import static com.example.masterplanbbe.domain.fixture.SpecFixture.createExistingSpec;
import static java.nio.charset.StandardCharsets.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ExamControllerTest {
    @InjectMocks
    private ExamController examController;
    @Mock
    private ExamService examService;

    private MockMvc mockMvc;

    private Member member;

    private Spec spec;

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(examController).build();
        member = createExistingMember();
        spec = createExistingSpec();
    }

    @Test
    @DisplayName("사용자는 시험을 조회하고 스펙 북마크 여부를 확인한다.")
    void retrieve_exam_and_check_bookmark_status() throws Exception {
        CustomPageRequest<ExamSortOption> request = new CustomPageRequest<>(0, 25, null, false);
        CustomPage<ExamItemCardDto> mocked = createMockedExamItemCardPage(spec, member);
        given(examService.getAllExam(request, member.getEmail())).willReturn(new PageResponse<>(mocked));

        ResultActions resultActions = mockMvc.perform(get("/api/v1/exams")
                .param("page", "0")
                .param("size", "25")
                .param("sortOption", (String) null)
                .param("isAsc", "false")
                .contentType(APPLICATION_JSON)
                .characterEncoding(UTF_8)
                .accept(APPLICATION_JSON)
                .principal(createMockPrincipal(member))
        );

        resultActions.andExpectAll(status().isOk(), content().contentType(APPLICATION_JSON_VALUE))
                .andDo(print())
                .andDo(mvcResult -> {
                    String responseContent = mvcResult.getResponse().getContentAsString(UTF_8);
                    ApiResponse<PageResponse<ExamItemCardDto>> response = objectMapper.readValue(responseContent, new TypeReference<>() {
                    });
                    PageResponse<ExamItemCardDto> data = response.getData();
                    assertThat(data).isNotNull();
                    assertAll(
                            () -> assertThat(data.content().size()).isEqualTo(2),
                            () -> assertThat(data.content().stream().allMatch(ExamItemCardDto::isBookmarked)).isFalse(),
                            () -> assertThat(data.content()).containsExactlyInAnyOrderElementsOf(mocked.content())
                    );
                });
    }

    private CustomPage<ExamItemCardDto> createMockedExamItemCardPage(Spec spec,
                                                                     Member member) {
        return new CustomPage<>(0, 25, 2, List.of(
                createExamItemCardDto(createExistingExamOf(spec.getExamDetails().get(0), 1L), false),
                createExamItemCardDto(createExistingExamOf(spec.getExamDetails().get(0), 2L), false)
        ));
    }

    @Disabled
    @Test
    @DisplayName("관리자는 시험을 추가한다")
    void addExam() throws Exception {
/*
        ExamCreateRequest request = createExamCreateRequest(spec.getExamDetails().get(0));
        CreateExamResponse mockedResult = new CreateExamResponse(createExistingExamOf(spec.getExamDetails().get(0), 1L));
        given(examService.create(any(ExamCreateRequest.class))).willReturn(mockedResult);

        ResultActions resultActions = mockMvc.perform(post("/api/v1/exams")
                .content(objectMapper.writeValueAsString(request))
                .contentType(APPLICATION_JSON)
                .characterEncoding(UTF_8)
                .accept(APPLICATION_JSON)
        );

        resultActions.andExpectAll(status().isOk(), content().contentType(APPLICATION_JSON_VALUE))
                .andDo(print())
                .andDo(mvcResult -> {
                    String responseContent = mvcResult.getResponse().getContentAsString(UTF_8);
                    ApiResponse<CreateExamResponse> response = objectMapper.readValue(responseContent, new TypeReference<>() {
                    });
                    CreateExamResponse data = response.getData();
                    assertThat(data).isNotNull();
                    assertAll(
                            () -> assertThat(data.name()).isEqualTo(request.name()),
                            () -> assertThat(data.participantCount()).isEqualTo(request.participantCount()),
                            () -> assertThat(data.applyStartDate()).isEqualTo(request.applyStartDate()),
                            () -> assertThat(data.applyEndDate()).isEqualTo(request.applyEndDate()),
                            () -> assertThat(data.examStartDate()).isEqualTo(request.examStartDate())
                    );
                });
*/
    }

    @Test
    @DisplayName("관리자는 시험을 삭제한다")
    void delete_exam() throws Exception {
        Long examId = 1L;
        willDoNothing().given(examService).delete(examId);

        ResultActions resultActions = mockMvc.perform(delete("/api/v1/exams/" + examId)
                .characterEncoding(UTF_8)
        );

        resultActions.andExpectAll(status().isOk(), content().contentType(APPLICATION_JSON_VALUE))
                .andDo(print())
                .andDo(mvcResult -> {
                    String responseContent = mvcResult.getResponse().getContentAsString(UTF_8);
                    ApiResponse<Void> response = objectMapper.readValue(responseContent, new TypeReference<>() {
                    });
                    assertThat(response).isNotNull();
                    assertThat(response.getMessage()).isEqualTo("시험 삭제 성공");
                });
    }
}
