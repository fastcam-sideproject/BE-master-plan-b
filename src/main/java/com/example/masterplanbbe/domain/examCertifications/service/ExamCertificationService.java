package com.example.masterplanbbe.domain.examCertifications.service;

import com.example.masterplanbbe.common.exception.ErrorCode;
import com.example.masterplanbbe.common.exception.GlobalException;
import com.example.masterplanbbe.domain.exam.repository.ExamRepository;
import com.example.masterplanbbe.domain.examCertifications.dto.request.ExamCertificationCreateRequest;
import com.example.masterplanbbe.domain.examCertifications.dto.response.ExamCertificationCreateResponse;
import com.example.masterplanbbe.domain.examCertifications.entity.ExamCertification;
import com.example.masterplanbbe.domain.examCertifications.repository.ExamCertificationRepository;
import com.example.masterplanbbe.member.entity.Member;
import com.example.masterplanbbe.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ExamCertificationService {

    private final ExamCertificationRepository examCertificationRepository;
    private final MemberRepository memberRepository;
    private final ExamRepository examRepository;

    @Transactional
    public ExamCertificationCreateResponse create(ExamCertificationCreateRequest createRequest, Long examId, String userId) {
        Member member = memberRepository.findByUserId(userId)
                .orElseThrow(() -> new GlobalException.NotFoundException(ErrorCode.USER_NOT_FOUND));

        ExamCertification examCertification = examCertificationRepository.save(
                createRequest.toEntity(
                        examRepository.getReferenceById(examId),
                        member));

        return ExamCertificationCreateResponse.from(examCertification);
    }
}
