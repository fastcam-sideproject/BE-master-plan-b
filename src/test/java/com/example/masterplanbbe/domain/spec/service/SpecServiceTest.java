package com.example.masterplanbbe.domain.spec.service;

import com.example.masterplanbbe.domain.exam.entity.Exam;
import com.example.masterplanbbe.domain.exam.entity.ExamDetail;
import com.example.masterplanbbe.domain.fixture.MemberFixture;
import com.example.masterplanbbe.domain.spec.dto.SpecItemCardDto;
import com.example.masterplanbbe.domain.spec.entity.Spec;
import com.example.masterplanbbe.domain.spec.repository.SpecRepositoryPort;
import com.example.masterplanbbe.domain.specBookmark.entity.SpecBookmark;
import com.example.masterplanbbe.member.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static com.example.masterplanbbe.domain.fixture.SpecBookmarkFixture.createSpecBookmark;
import static com.example.masterplanbbe.domain.fixture.SpecFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("스펙 서비스 테스트")
public class SpecServiceTest {
    @InjectMocks
    SpecService specService;
    @Mock
    SpecRepositoryPort specRepositoryPort;

    @Test
    @DisplayName("사용자는 스펙을 조회하고 북마크 여부를 확인한다.")
    void get_spec_and_check_bookmark() {
        Member member = MemberFixture.createMember();
        PageRequest pageRequest = PageRequest.of(0, 25);
        Page<SpecItemCardDto> mocked = createMockedSpecItemCardPage(member);
        given(specRepositoryPort.getSpecItemCards(pageRequest, member.getId())).willReturn(mocked);

        Page<SpecItemCardDto> result = specService.getAllSpec(pageRequest, member.getId());

        verify(specRepositoryPort, times(1)).getSpecItemCards(pageRequest, member.getId());
        assertSpecItemCardPage(result);
    }

    private Page<SpecItemCardDto> createMockedSpecItemCardPage(Member member) {
        Spec spec1 = createExistingSpecFrom(1L);
        Spec spec2 = createExistingSpecFrom(2L);
        SpecBookmark specBookmark1 = createSpecBookmark(member, spec1);

        return new PageImpl<>(List.of(
                new SpecItemCardDto(spec1, null, isBookmarkedBy(spec1, specBookmark1)),
                new SpecItemCardDto(spec2, null, isBookmarkedBy(spec2, specBookmark1))
        ));
    }

    private void assertSpecItemCardPage(Page<SpecItemCardDto> result) {
        assertThat(result).isNotNull();
        assertAll(
                () -> assertThat(result.getContent().size()).isEqualTo(2),
                () -> assertThat(result.getContent().get(0).isBookmarked()).isTrue(),
                () -> assertThat(result.getContent().get(1).isBookmarked()).isFalse()
        );
    }

    private Boolean isBookmarkedBy(Spec spec,
                                   SpecBookmark specBookmark) {
        return spec.getId().equals(specBookmark.getSpec().getId());
    }
}
