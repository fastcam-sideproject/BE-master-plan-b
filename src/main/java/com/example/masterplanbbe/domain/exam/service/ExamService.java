package com.example.masterplanbbe.domain.exam.service;

import com.example.masterplanbbe.common.page.CustomPage;
import com.example.masterplanbbe.common.request.CustomPageRequest;
import com.example.masterplanbbe.common.response.PageResponse;
import com.example.masterplanbbe.domain.exam.dto.ExamItemCardDto;
import com.example.masterplanbbe.domain.exam.entity.Exam;
import com.example.masterplanbbe.domain.exam.enums.ExamSortOption;
import com.example.masterplanbbe.domain.exam.repository.ExamRepositoryPort;
import com.example.masterplanbbe.domain.exam.request.ExamCreateRequest;
import com.example.masterplanbbe.domain.exam.request.ExamUpdateRequest;
import com.example.masterplanbbe.domain.exam.response.CreateExamResponse;
import com.example.masterplanbbe.domain.exam.response.ReadExamResponse;
import com.example.masterplanbbe.domain.exam.response.UpdateExamResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExamService {
    private final ExamRepositoryPort examRepositoryPort;

    public PageResponse<ExamItemCardDto> getAllExam(CustomPageRequest<ExamSortOption> request,
                                                    String email) {
        return new PageResponse<>(examRepositoryPort.getExamItemCards(request, email));
    }

    public ReadExamResponse getExam(Long examId, String email) {
        return new ReadExamResponse(examRepositoryPort.getExamWithDetails(examId, email));
    }

    @Transactional
    public CreateExamResponse create(ExamCreateRequest request) {
        return new CreateExamResponse(examRepositoryPort.save(request.toEntity()));
    }

    @Transactional
    public void delete(Long examId) {
        examRepositoryPort.deleteById(examId);
    }

    @Transactional
    public UpdateExamResponse update(Long examId,
                                     ExamUpdateRequest request) {
        Exam exam = examRepositoryPort.getById(examId);
        request.update(exam);
        return new UpdateExamResponse(exam);
    }
}
