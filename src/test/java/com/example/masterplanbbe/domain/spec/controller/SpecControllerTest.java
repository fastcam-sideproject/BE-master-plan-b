package com.example.masterplanbbe.domain.spec.controller;

import com.example.masterplanbbe.common.page.CustomPage;
import com.example.masterplanbbe.common.request.CustomPageRequest;
import com.example.masterplanbbe.domain.member.entity.Member;
import com.example.masterplanbbe.domain.spec.dto.SpecItemCardDto;
import com.example.masterplanbbe.domain.spec.entity.Spec;
import com.example.masterplanbbe.domain.spec.enums.SpecSortOption;
import com.example.masterplanbbe.domain.spec.service.SpecService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.example.masterplanbbe.domain.fixture.MemberFixture.*;
import static com.example.masterplanbbe.domain.fixture.SpecFixture.*;

@ExtendWith(MockitoExtension.class)
public class SpecControllerTest {
    @InjectMocks
    private SpecController specController;

    @Mock
    private SpecService specService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(specController).build();
    }

    @Test
    @DisplayName("사용자는 스펙 목록을 조회한다.")
    void getSpecList() {
        Member member = createMember();
        CustomPageRequest<SpecSortOption> request = new CustomPageRequest<>(0, 25, null, false);
        CustomPage<SpecItemCardDto> mockedPage = createMockedSpecItemCardPage();
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
    void getSpecDetail() {

    }

    @Test
    @DisplayName("관리자는 스펙을 추가한다")
    void addSpec() {

    }

    @Test
    @DisplayName("관리자는 스펙을 수정한다")
    void updateSpec() {

    }

    @Test
    @DisplayName("관리자는 스펙을 삭제한다")
    void deleteSpec() {

    }
}
