package com.example.masterplanbbe.domain.exam.repository;

import com.example.masterplanbbe.domain.fixture.MemberFixture;
import com.example.masterplanbbe.exam.dto.ExamItemCardDto;
import com.example.masterplanbbe.exam.entity.Exam;
import com.example.masterplanbbe.exam.entity.ExamBookmark;
import com.example.masterplanbbe.exam.repository.ExamBookmarkRepository;
import com.example.masterplanbbe.exam.repository.ExamRepository;
import com.example.masterplanbbe.member.entity.Member;
import com.example.masterplanbbe.member.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static com.example.masterplanbbe.domain.fixture.ExamFixture.createExam;
import static com.example.masterplanbbe.domain.fixture.MemberFixture.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class ExamRepositoryTest {
    @Autowired private ExamRepository examRepository;
    @Autowired private MemberRepository memberRepository;
    @Autowired private ExamBookmarkRepository examBookmarkRepository;

    @BeforeEach
    void setUp() {
        examBookmarkRepository.deleteAll();
        examRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("사용자는 시험을 조회하고 북마크 여부를 확인할 수 있다.")
    void retrieve_exam_and_check_bookmark_status() {
//        Member member = memberRepository.save(createMember());
//        Exam exam1 = createExam("exam1");
//        Exam exam2 = createExam("exam2");
//        examRepository.saveAll(List.of(exam1, exam2));
//        ExamBookmark examBookmark = examBookmarkRepository.save(new ExamBookmark(member, exam1));
//        PageRequest pageRequest = PageRequest.of(0, 25);
//
//        Page<ExamItemCardDto> result = examRepository.getExamItemCards(pageRequest, member.getUserId());
//
//        assertThat(result.getContent().size()).isEqualTo(2);
//        assertThat(result.getContent().get(0).title()).isEqualTo("exam2");
//        assertThat(result.getContent().get(1).title()).isEqualTo("exam1");
//        assertThat(result.getContent().get(0).isBookmarked()).isFalse();
    }

}
