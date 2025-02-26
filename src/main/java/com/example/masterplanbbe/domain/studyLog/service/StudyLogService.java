package com.example.masterplanbbe.domain.studyLog.service;

import com.example.masterplanbbe.domain.exam.entity.Exam;
import com.example.masterplanbbe.domain.exam.repository.ExamRepositoryPort;
import com.example.masterplanbbe.domain.studyLog.entity.StudyLog;
import com.example.masterplanbbe.domain.studyLog.repository.StudyLogRepositoryPort;
import com.example.masterplanbbe.domain.studyLog.request.StudyLogRequest;
import com.example.masterplanbbe.domain.studyLog.response.StudyLogResponse;
import com.example.masterplanbbe.member.entity.Member;
import com.example.masterplanbbe.member.repository.MemberRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StudyLogService {
    private final MemberRepositoryPort memberRepositoryPort;
    private final StudyLogRepositoryPort studyLogRepositoryPort;
    private final ExamRepositoryPort examRepositoryPort;

    @Transactional
    public StudyLogResponse create(StudyLogRequest studyLogRequest, String memberId) {

        Member member = memberRepositoryPort.findByUserId(memberId);

        Exam exam = examRepositoryPort.getById(studyLogRequest.examId());

        StudyLog studyLog = studyLogRepositoryPort.save(StudyLog.of(member, exam, studyLogRequest.studyDate(), studyLogRequest.hour(), studyLogRequest.minute(), studyLogRequest.content(), studyLogRequest.inputSource()));

        return StudyLogResponse.from(studyLog);
    }
}
