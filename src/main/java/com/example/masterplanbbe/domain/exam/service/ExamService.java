package com.example.masterplanbbe.domain.exam.service;

import com.example.masterplanbbe.domain.exam.dto.ExamItemCardDto;
import com.example.masterplanbbe.domain.exam.repository.ExamRepositoryPort;
import com.example.masterplanbbe.domain.exam.request.ExamCreateRequest;
import com.example.masterplanbbe.domain.exam.response.CreateExamResponse;
import com.example.masterplanbbe.domain.exam.response.ReadExamResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExamService {
    private final ExamRepositoryPort examRepositoryPort;

    public Page<ExamItemCardDto> getAll(Pageable pageable, String memberId) {
        return examRepositoryPort.getExamItemCards(pageable, memberId);
    }

    public ReadExamResponse getOne(Long memberId) {
        return new ReadExamResponse(examRepository.getExam(memberId));
    }

    public CreateExamResponse create(ExamCreateRequest request) {
        return new CreateExamResponse(examRepositoryPort.save(request.toEntity()));
    }
}
