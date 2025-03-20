package com.example.masterplanbbe.domain.service;

import com.example.masterplanbbe.presentation.request.CustomPageRequest;
import com.example.masterplanbbe.presentation.response.PageResponse;
import com.example.masterplanbbe.application.dto.ExamItemCardDto;
import com.example.masterplanbbe.domain.entity.Exam;
import com.example.masterplanbbe.domain.enums.ExamSortOption;
import com.example.masterplanbbe.domain.repository.ExamRepositoryPort;
import com.example.masterplanbbe.presentation.request.ExamCreateRequest;
import com.example.masterplanbbe.presentation.request.ExamUpdateRequest;
import com.example.masterplanbbe.presentation.response.CreateExamResponse;
import com.example.masterplanbbe.presentation.response.ReadExamResponse;
import com.example.masterplanbbe.presentation.response.UpdateExamResponse;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExamService {
    private final ExamRepositoryPort examRepositoryPort;
    private final EntityManager entityManager;

    public PageResponse<ExamItemCardDto> getAllExam(CustomPageRequest<ExamSortOption> request,
                                                    String email) {
        return new PageResponse<>(examRepositoryPort.getExamItemCards(request, email));
    }

    public ReadExamResponse getExam(Long examId, String email) {
        return new ReadExamResponse(examRepositoryPort.getExamWithDetails(examId, email));
    }

    @Transactional
    public CreateExamResponse create(ExamCreateRequest request) {
        return new CreateExamResponse(examRepositoryPort.save(request.toEntity(entityManager)));
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
