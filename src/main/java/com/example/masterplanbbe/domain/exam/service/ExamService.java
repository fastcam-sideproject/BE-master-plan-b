package com.example.masterplanbbe.domain.exam.service;

import com.example.masterplanbbe.domain.exam.dto.ExamItemCardDto;
import com.example.masterplanbbe.domain.exam.repository.ExamRepository;
import com.example.masterplanbbe.domain.exam.response.ReadExamResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExamService {
    private final ExamRepository examRepository;

    public Page<ExamItemCardDto> getAllExam(Pageable pageable, String memberId) {
        return examRepository.getExamItemCards(pageable, memberId);
    }

    public ReadExamResponse getExam(Long memberId) {
        return null; //TODO: Implement this method
//        return new ReadExamResponse(examRepository.getExam(memberId));
    }
}
