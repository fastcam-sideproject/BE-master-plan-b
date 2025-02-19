package com.example.masterplanbbe.domain.examCertifications.controller;


import com.example.masterplanbbe.common.response.ApiResponse;
import com.example.masterplanbbe.domain.exam.entity.Exam;
import com.example.masterplanbbe.domain.examCertifications.controller.ExamCertificationController;
import com.example.masterplanbbe.domain.examCertifications.dto.request.ExamCertificationCreateRequest;
import com.example.masterplanbbe.domain.examCertifications.dto.response.ExamCertificationCreateResponse;
import com.example.masterplanbbe.domain.examCertifications.entity.ExamCertification;
import com.example.masterplanbbe.domain.examCertifications.enums.PriorLearningLevel;
import com.example.masterplanbbe.domain.examCertifications.service.AuthService;
import com.example.masterplanbbe.domain.examCertifications.service.ExamCertificationService;
import com.example.masterplanbbe.member.entity.Member;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.util.ReflectionTestUtils.setField;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@DisplayName("시험 인증 컨트롤러 테스트")
@ExtendWith(MockitoExtension.class)
class ExamCertificationControllerTest {
    private MockMvc mockMvc;

    @Mock
    private ExamCertificationService examCertificationService;
    @Mock
    private AuthService authService;

    @InjectMocks
    private ExamCertificationController examCertificationController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(examCertificationController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .build();
    }

    @Nested
    @DisplayName("시험 인증 생성 API 테스트")
    public class NestedClass {
        private static String EXAM_CERTIFICATION_CREATE_URI = "/api/v1/exam/{examId}/certification";

        @DisplayName("정상 처리")
        @Test
        public void success() throws Exception {
            //given
            ExamCertificationCreateRequest createRequest =
                    new ExamCertificationCreateRequest(PriorLearningLevel.MAJOR, 10, "3달");

            Long examId = 456L;
            String userId = "userID";

            ExamCertification certification = getExamCertification(createRequest);
            ExamCertificationCreateResponse mockResponse = ExamCertificationCreateResponse.from(certification);

            BDDMockito.given(examCertificationService.create(createRequest, examId, userId))
                    .willReturn(mockResponse);
            BDDMockito.given(authService.getUserId())
                    .willReturn(userId);

            //when
            ResultActions action = mockMvc.perform(post(EXAM_CERTIFICATION_CREATE_URI, examId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(createRequest)));

            //then
            action.andExpect(status().isOk())
                    .andDo(mvcResult -> {
                        String responseContent = mvcResult.getResponse().getContentAsString(UTF_8);
                        ApiResponse<ExamCertificationCreateResponse> response = objectMapper.readValue(responseContent, new TypeReference<>() {
                        });

                        ExamCertificationCreateResponse result = response.getData();

                        assertAll(
                                () -> assertThat(result.examCertificationId()).isNotNull(),
                                () -> assertThat(result.learningPeriod()).isEqualTo(createRequest.learningPeriod()),
                                () -> assertThat(result.dailyStudyHours()).isEqualTo(createRequest.dailyStudyHours()),
                                () -> assertThat(result.priorLearningLevel()).isEqualTo(createRequest.priorLearningLevel()),
                                () -> assertThat(result.member().nickname()).isEqualTo(certification.getMember().getNickname()),
                                () -> assertThat(result.member().profileImageUrl()).isEqualTo(certification.getMember().getProfileImageUrl()),
                                () -> assertThat(result.exam().title()).isEqualTo(certification.getExam().getTitle())
                        );

                    });

        }
    }


    private static ExamCertification getExamCertification(ExamCertificationCreateRequest createRequest) {
        ExamCertification build = ExamCertification.builder()
                .learningPeriod(createRequest.learningPeriod())
                .dailyStudyHours(createRequest.dailyStudyHours())
                .priorLearningLevel(createRequest.priorLearningLevel())
                .viewCount(0)
                .exam(Exam.builder()
                        .title("title")
                        .build())
                .member(Member.builder()
                        .nickname("nickname")
                        .profileImageUrl("url")
                        .build())
                .build();
        setField(build, "id", 5L);
        return build;
    }
}