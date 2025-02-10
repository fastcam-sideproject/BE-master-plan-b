package com.example.masterplanbbe.domain.examBookmark.service;

import com.example.masterplanbbe.domain.exam.entity.Exam;
import com.example.masterplanbbe.domain.exam.repository.ExamRepositoryPort;
import com.example.masterplanbbe.domain.examBookmark.entity.ExamBookmark;
import com.example.masterplanbbe.domain.examBookmark.repository.ExamBookmarkRepository;
import com.example.masterplanbbe.domain.examBookmark.response.CreateExamBookmarkResponse;
import com.example.masterplanbbe.member.entity.Member;
import com.example.masterplanbbe.member.repository.MemberRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExamBookmarkService {
    private final MemberRepositoryPort memberRepositoryPort;
    private final ExamRepositoryPort examRepositoryPort;
    private final ExamBookmarkRepository examBookmarkRepository;

    public CreateExamBookmarkResponse createExamBookmark(Long memberId, Long examId) {
        Member member = memberRepositoryPort.findById(memberId);
        Exam exam = examRepositoryPort.getById(examId);
        return new CreateExamBookmarkResponse(examBookmarkRepository.save(new ExamBookmark(member, exam)));
    }

    public void deleteExamBookmark(Long examBookmarkId) {
        examBookmarkRepository.deleteById(examBookmarkId);
    }
}
