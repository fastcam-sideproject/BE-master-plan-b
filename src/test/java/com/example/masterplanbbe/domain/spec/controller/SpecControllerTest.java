package com.example.masterplanbbe.domain.spec.controller;

import com.example.masterplanbbe.common.page.CustomPage;
import com.example.masterplanbbe.common.request.CustomPageRequest;
import com.example.masterplanbbe.common.response.ApiResponse;
import com.example.masterplanbbe.common.response.PageResponse;
import com.example.masterplanbbe.domain.exam.enums.CertificationType;
import com.example.masterplanbbe.domain.member.entity.Member;
import com.example.masterplanbbe.domain.spec.dto.SpecItemCardDto;
import com.example.masterplanbbe.domain.spec.entity.Spec;
import com.example.masterplanbbe.domain.spec.enums.SpecSortOption;
import com.example.masterplanbbe.domain.spec.request.SpecCreateRequest;
import com.example.masterplanbbe.domain.spec.request.SpecUpdateRequest;
import com.example.masterplanbbe.domain.spec.response.CreateSpecResponse;
import com.example.masterplanbbe.domain.spec.response.ReadSpecResponse;
import com.example.masterplanbbe.domain.spec.response.UpdateSpecResponse;
import com.example.masterplanbbe.domain.spec.service.SpecService;
import com.example.masterplanbbe.utils.TestUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.example.masterplanbbe.domain.exam.enums.CertificationType.*;
import static com.example.masterplanbbe.domain.fixture.MemberFixture.*;
import static com.example.masterplanbbe.domain.fixture.SpecFixture.*;
import static com.example.masterplanbbe.utils.TestUtils.*;
import static java.nio.charset.StandardCharsets.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.util.ReflectionTestUtils.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class SpecControllerTest {
    @InjectMocks
    private SpecController specController;

    @Mock
    private SpecService specService;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(specController).build();
    }

    @Test
    @DisplayName("사용자는 스펙을 조회하고 북마크 여부를 확인한다.")
    void retrieve_spec_and_check_bookmark_status() throws Exception {
        Member member = createExistingMember();
        CustomPageRequest<SpecSortOption> request = new CustomPageRequest<>(0, 25, null, false);
        CustomPage<SpecItemCardDto> mockedPage = createMockedSpecItemCardPage();
        given(specService.getAllSpec(request, member.getId())).willReturn(new PageResponse<>(mockedPage));

        ResultActions resultActions = mockMvc.perform(get("/api/v1/specs")
                .param("page", "0")
                .param("size", "25")
                .param("sortOption", (String) null)
                .param("isAsc", "false")
                .param("memberId", member.getId().toString())
                .contentType(APPLICATION_JSON)
                .characterEncoding(UTF_8)
                .accept(APPLICATION_JSON));

        resultActions.andExpectAll(status().isOk(), content().contentType(APPLICATION_JSON))
                .andDo(print())
                .andDo(mvcResult -> {
                    String responseContent = mvcResult.getResponse().getContentAsString(UTF_8);
                    ApiResponse<PageResponse<SpecItemCardDto>> response = objectMapper.readValue(responseContent, new TypeReference<>() {
                    });
                    PageResponse<SpecItemCardDto> data = response.getData();
                    assertThat(data).isNotNull();
                    assertThat(data.content()).isNotNull();
                    assertAll(
                            () -> assertThat(data.content().size()).isEqualTo(2),
                            () -> assertThat(data.content()).usingRecursiveFieldByFieldElementComparator()
                                    .containsExactlyInAnyOrderElementsOf(mockedPage.content())
                    );
                });
    }

    private CustomPage<SpecItemCardDto> createMockedSpecItemCardPage() {
        Spec spec1 = createExistingSpecFrom(1L);
        Spec spec2 = createExistingSpecFrom(2L);
        List<SpecItemCardDto> specItemCardDtoList = List.of(
                createSpecItemCardDto(spec1, false),
                createSpecItemCardDto(spec2, false)
        );

        return new CustomPage<>(0, 25, specItemCardDtoList.size(), specItemCardDtoList);
    }

    @Test
    @DisplayName("사용자는 스펙을 상세 조회한다")
    void getSpecDetail() throws Exception {
        Member member = createExistingMember();
        Long specId = 1L;
        ReadSpecResponse mockedResult = new ReadSpecResponse(createSpecWithDetailsDto(createExistingSpecFrom(specId)));
        given(specService.getSpec(specId)).willReturn(mockedResult);

        ResultActions resultActions = mockMvc.perform(get("/api/v1/specs/{specId}", specId)
                .contentType(APPLICATION_JSON)
                .characterEncoding(UTF_8)
                .accept(APPLICATION_JSON));

        resultActions.andExpectAll(status().isOk(), content().contentType(APPLICATION_JSON))
                .andDo(print())
                .andDo(mvcResult -> {
                    String responseContent = mvcResult.getResponse().getContentAsString(UTF_8);
                    ApiResponse<ReadSpecResponse> response = objectMapper.readValue(responseContent, new TypeReference<>() {
                    });
                    ReadSpecResponse data = response.getData();
                    assertThat(data).isNotNull();
                    assertAll(
                            () -> assertThat(data.name()).isEqualTo(mockedResult.name()),
                            () -> assertThat(data.issuingOrganization()).isEqualTo(mockedResult.issuingOrganization()),
                            () -> assertThat(data.certificationType()).isEqualTo(mockedResult.certificationType()),
                            () -> assertThat(data.preparation()).isEqualTo(mockedResult.preparation()),
                            () -> assertThat(data.eligibility()).isEqualTo(mockedResult.eligibility()),
                            () -> assertThat(data.examStructure()).isEqualTo(mockedResult.examStructure()),
                            () -> assertThat(data.passingCriteria()).isEqualTo(mockedResult.passingCriteria())
                    );
                });
    }

    @Test
    @DisplayName("관리자는 스펙을 추가한다")
    void addSpec() throws Exception {
        SpecCreateRequest request = createSpecCreateRequest();
        CreateSpecResponse mockedResult = new CreateSpecResponse(createExistingSpec());
        given(specService.create(request)).willReturn(mockedResult);

        ResultActions resultActions = mockMvc.perform(post("/api/v1/specs")
                .contentType(APPLICATION_JSON)
                .characterEncoding(UTF_8)
                .content(objectMapper.writeValueAsString(request))
                .accept(APPLICATION_JSON));

        resultActions.andExpectAll(status().isOk(), content().contentType(APPLICATION_JSON))
                .andDo(print())
                .andDo(mvcResult -> {
                    String responseContent = mvcResult.getResponse().getContentAsString(UTF_8);
                    ApiResponse<CreateSpecResponse> response = objectMapper.readValue(responseContent, new TypeReference<>() {
                    });
                    CreateSpecResponse data = response.getData();
                    assertThat(data).isNotNull();
                    assertAll(
                            () -> assertThat(data.name()).isEqualTo(request.name()),
                            () -> assertThat(data.issuingOrganization()).isEqualTo(request.issuingOrganization()),
                            () -> assertThat(data.certificationType()).isEqualTo(request.certificationType()),
                            () -> assertThat(data.preparation()).isEqualTo(request.preparation()),
                            () -> assertThat(data.eligibility()).isEqualTo(request.eligibility()),
                            () -> assertThat(data.examStructure()).isEqualTo(request.examStructure()),
                            () -> assertThat(data.passingCriteria()).isEqualTo(request.passingCriteria())
                    );
                });
    }

    @Test
    @DisplayName("관리자는 스펙을 수정한다")
    void updateSpec() throws Exception {
        Long specId = 1L;
        Spec spec = createExistingSpecFrom(specId);
        SpecUpdateRequest request = createSpecUpdateRequest(spec, NATIONAL_CERTIFIED);
        given(specService.update(specId, request)).willReturn(new UpdateSpecResponse(
                        createUpdatedSpec(() -> spec, NATIONAL_CERTIFIED)
                )
        );

        ResultActions resultActions = mockMvc.perform(patch("/api/v1/specs/{specId}", specId)
                .contentType(APPLICATION_JSON)
                .characterEncoding(UTF_8)
                .content(objectMapper.writeValueAsString(request))
                .accept(APPLICATION_JSON));

        resultActions.andExpectAll(status().isOk(), content().contentType(APPLICATION_JSON))
                .andDo(print())
                .andDo(mvcResult -> {
                    String responseContent = mvcResult.getResponse().getContentAsString(UTF_8);
                    ApiResponse<UpdateSpecResponse> response = objectMapper.readValue(responseContent, new TypeReference<>() {
                    });
                    UpdateSpecResponse data = response.getData();
                    assertThat(data).isNotNull();
                    assertAll(
                            () -> assertThat(data.name()).isEqualTo(request.name()),
                            () -> assertThat(data.issuingOrganization()).isEqualTo(request.issuingOrganization()),
                            () -> assertThat(data.certificationType()).isEqualTo(request.certificationType()),
                            () -> assertThat(data.difficulty()).isEqualTo(request.difficulty()),
                            () -> assertThat(data.participantCount()).isEqualTo(request.participantCount())
                    );
                });
    }

    @Test
    @DisplayName("관리자는 스펙을 삭제한다")
    void deleteSpec() throws Exception {
        Long specId = 1L;
        willDoNothing().given(specService).delete(specId);

        ResultActions resultActions = mockMvc.perform(delete("/api/v1/specs/{specId}", specId)
                .characterEncoding(UTF_8));

        resultActions.andExpectAll(status().isOk(), content().contentType(APPLICATION_JSON))
                .andDo(print())
                .andDo(mvcResult -> {
                    String responseContent = mvcResult.getResponse().getContentAsString(UTF_8);
                    ApiResponse<Void> response = objectMapper.readValue(responseContent, new TypeReference<>() {
                    });
                    assertThat(response).isNotNull();
                    assertThat(response.getMessage()).isEqualTo("스펙 삭제 성공");
                });
    }
}
