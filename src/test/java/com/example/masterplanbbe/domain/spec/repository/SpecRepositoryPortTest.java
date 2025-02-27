package com.example.masterplanbbe.domain.spec.repository;


import com.example.masterplanbbe.domain.fixture.MemberFixture;
import com.example.masterplanbbe.domain.spec.dto.SpecItemCardDto;
import com.example.masterplanbbe.domain.spec.dto.SpecWithDetailsDto;
import com.example.masterplanbbe.domain.spec.entity.Spec;
import com.example.masterplanbbe.domain.specBookmark.entity.SpecBookmark;
import com.example.masterplanbbe.domain.specBookmark.repository.SpecBookmarkRepository;
import com.example.masterplanbbe.member.entity.Member;
import com.example.masterplanbbe.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static com.example.masterplanbbe.domain.fixture.MemberFixture.*;
import static com.example.masterplanbbe.domain.fixture.SpecBookmarkFixture.createSpecBookmark;
import static com.example.masterplanbbe.domain.fixture.SpecFixture.createSpec;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
@DisplayName("스펙 레포지토리 테스트")
public class SpecRepositoryPortTest {
    @Autowired private SpecRepositoryPort specRepositoryPort;
    @Autowired private MemberRepository memberRepository;
    @Autowired private SpecBookmarkRepository specBookmarkRepository;

    @BeforeEach
    void setUp() {
        specBookmarkRepository.deleteAll();
        specRepositoryPort.deleteAll();
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("사용자는 스펙을 조회하고 북마크 여부를 확인할 수 있다.")
    void retrieve_spec_and_check_bookmark_status() {
        Member member = memberRepository.save(createMember());
        Spec spec1 = createSpec();
        Spec spec2 = createSpec();
        specRepositoryPort.saveAll(List.of(spec1, spec2));
        SpecBookmark specBookmark = specBookmarkRepository.save(createSpecBookmark(member, spec1));
        PageRequest pageRequest = PageRequest.of(0, 25);

        Page<SpecItemCardDto> result = specRepositoryPort.getSpecItemCards(pageRequest, member.getId());

        assertThat(result.getContent().size()).isEqualTo(2);
        assertAll(
                () -> assertThat(result.getContent().get(0).name()).isEqualTo(spec1.getName()),
                () -> assertThat(result.getContent().get(1).name()).isEqualTo(spec2.getName()),
                () -> assertThat(result.getContent().get(0).isBookmarked()).isTrue(),
                () -> assertThat(result.getContent().get(1).isBookmarked()).isFalse()
        );
    }

    //TODO: ExamDetail 의 필드들은 테스트에서 확인되고 있지 않음
    @Test
    @DisplayName("사용자는 스펙의 상세 정보를 조회할 수 있다")
    void retrieve_spec_detail() {
        Spec spec = specRepositoryPort.save(createSpec());

        SpecWithDetailsDto result = specRepositoryPort.getSpecWithDetails(spec.getId());

        assertThat(result).isNotNull();
        assertAll(
                () -> assertThat(result.name()).isEqualTo(spec.getName()),
                () -> assertThat(result.issuingOrganization()).isEqualTo(spec.getIssuingOrganization()),
                () -> assertThat(result.certificationType()).isEqualTo(spec.getCertificationType()),
                () -> assertThat(result.isBookmarked()).isFalse()
        );
    }
}
