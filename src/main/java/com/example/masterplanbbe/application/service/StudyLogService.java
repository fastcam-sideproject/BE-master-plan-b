package com.example.masterplanbbe.application.service;

import com.example.masterplanbbe.domain.entity.Exam;
import com.example.masterplanbbe.domain.repository.ExamRepositoryPort;
import com.example.masterplanbbe.domain.entity.Member;
import com.example.masterplanbbe.domain.repository.MemberRepositoryPort;
import com.example.masterplanbbe.domain.entity.StudyLog;
import com.example.masterplanbbe.domain.repository.StudyLogRepositoryPort;
import com.example.masterplanbbe.presentation.request.StudyLogRequest;
import com.example.masterplanbbe.presentation.response.StudyLogResponse;
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

        Member member = memberRepositoryPort.findByEmail(memberId);

        Exam exam = examRepositoryPort.getById(studyLogRequest.examId());

        StudyLog studyLog = studyLogRepositoryPort.save(StudyLog.of(member, exam, studyLogRequest.studyDate(), studyLogRequest.hour(), studyLogRequest.minute(), studyLogRequest.content(), studyLogRequest.inputSource()));

        return StudyLogResponse.from(studyLog);
    }

    @Transactional
    public StudyLogResponse update(StudyLogRequest studyLogRequest, Long studyLogId, String memberId) {
        StudyLog studyLog = studyLogRepositoryPort.findByIdAndMemberId(studyLogId, memberId);

        Exam exam = examRepositoryPort.getById(studyLogRequest.examId());

        studyLog.updateStudyLog(studyLogRequest, exam);

        return StudyLogResponse.from(studyLog);
    }
}
