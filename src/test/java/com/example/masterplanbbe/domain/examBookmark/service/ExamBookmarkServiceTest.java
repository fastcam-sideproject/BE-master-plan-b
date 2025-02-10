package com.example.masterplanbbe.domain.examBookmark.service;

import com.example.masterplanbbe.domain.examBookmark.repository.ExamBookmarkRepository;
import com.example.masterplanbbe.member.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.example.masterplanbbe.domain.fixture.MemberFixture.createMember;

@ExtendWith(MockitoExtension.class)
@DisplayName("시험 북마크 서비스 테스트")
public class ExamBookmarkServiceTest {
    @InjectMocks
    ExamBookmarkService examBookmarkService;
    @Mock
    ExamBookmarkRepository examBookmarkRepository;

    @Test
    @DisplayName("사용자는 시험 북마크를 추가한다.")
    void add_exam_bookmark() {
        Member member = createMember();
        
    }
}
