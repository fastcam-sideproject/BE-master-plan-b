package com.example.masterplanbbe.domain.exam.repository;

import com.example.masterplanbbe.exam.repository.ExamBookmarkRepository;
import com.example.masterplanbbe.exam.repository.ExamRepository;
import com.example.masterplanbbe.member.entity.Member;
import com.example.masterplanbbe.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.example.masterplanbbe.domain.fixture.MemberFixture.*;

@SpringBootTest
public class ExamRepositoryTest {
    @Autowired private ExamRepository examRepository;
    @Autowired private MemberRepository memberRepository;
    @Autowired private ExamBookmarkRepository examBookmarkRepository;
    private Member savedMember;

    @BeforeEach
    void setUp() {
        savedMember = memberRepository.save(createMember());
    }

    @Test
    void test() {
        // test code
    }
}
