package com.example.masterplanbbe.domain.exam.repository;

import com.example.masterplanbbe.exam.repository.ExamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ExamRepositoryTest {
    @Autowired private ExamRepository examRepository;

    @BeforeEach
    void setUp() {
    }
}
