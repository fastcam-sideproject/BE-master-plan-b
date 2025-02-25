package com.example.masterplanbbe.domain.specBookmark.service;

import com.example.masterplanbbe.domain.spec.entity.Spec;
import com.example.masterplanbbe.domain.spec.repository.SpecRepositoryPort;
import com.example.masterplanbbe.domain.specBookmark.entity.SpecBookmark;
import com.example.masterplanbbe.domain.specBookmark.repository.SpecBookmarkRepository;
import com.example.masterplanbbe.domain.specBookmark.response.CreateSpecBookmarkResponse;
import com.example.masterplanbbe.member.entity.Member;
import com.example.masterplanbbe.member.repository.MemberRepositoryPort;
import com.example.masterplanbbe.utils.TestUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.example.masterplanbbe.domain.fixture.MemberFixture.creatfExistingMember;
import static com.example.masterplanbbe.domain.fixture.SpecFixture.createExistingSpec;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("스펙 북마크 서비스 테스트")
public class SpecBookmarkServiceTest {
    @InjectMocks
    SpecBookmarkService specBookmarkService;
    @Mock
    SpecBookmarkRepository specBookmarkRepository;
    @Mock
    MemberRepositoryPort memberRepositoryPort;
    @Mock
    SpecRepositoryPort specRepositoryPort;

    @Test
    @DisplayName("사용자는 스펙 북마크를 추가한다.")
    void add_spec_bookmark() {
        Member member = creatfExistingMember();
        Spec spec = createExistingSpec();
        given(memberRepositoryPort.findById(anyLong())).willReturn(member);
        given(specRepositoryPort.getById(anyLong())).willReturn(spec);
        given(specBookmarkRepository.save(any(SpecBookmark.class))).willAnswer(TestUtils::simulateSavingEntity);

        CreateSpecBookmarkResponse result = specBookmarkService.createExamBookmark(member.getId(), spec.getId());

        verify(memberRepositoryPort, times(1)).findById(anyLong());
        verify(specRepositoryPort, times(1)).getById(anyLong());
        verify(specBookmarkRepository, times(1)).save(any(SpecBookmark.class));
        assertAll(
                () -> assertThat(result.examBookmarkId()).isNotNull(),
                () -> assertThat(result.memberId()).isEqualTo(member.getId()),
                () -> assertThat(result.specId()).isEqualTo(spec.getId())
        );
    }

    @Test
    @DisplayName("사용자는 스펙 북마크를 삭제한다.")
    void delete_spec_bookmark() {
        Long examBookmarkId = 1L;
        willDoNothing().given(specRepositoryPort).deleteById(examBookmarkId);

        specBookmarkService.deleteExamBookmark(examBookmarkId);

        verify(specBookmarkRepository, times(1)).deleteById(anyLong());
    }

}
