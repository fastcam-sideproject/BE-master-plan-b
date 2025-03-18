package com.example.masterplanbbe.infrastructure.repository;

import com.example.masterplanbbe.domain.entity.StudyLog;
import com.example.masterplanbbe.domain.repository.StudyLogRepository;
import com.example.masterplanbbe.domain.repository.StudyLogRepositoryPort;
import com.example.masterplanbbe.presentation.response.StudyLogResponse;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.masterplanbbe.domain.entity.QStudyLog.studyLog;
import static com.example.masterplanbbe.infrastructure.exception.ErrorCode.NOT_FOUND_STUDY_LOG;
import static com.example.masterplanbbe.infrastructure.exception.GlobalException.NotFoundException;

@Repository
@RequiredArgsConstructor
public class StudyLogRepositoryAdapter implements StudyLogRepositoryPort {

    private final StudyLogRepository studyLogRepository;
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public StudyLog save(StudyLog studyLog) {
        return studyLogRepository.save(studyLog);
    }

    @Override
    public void delete(StudyLog studyLog) {
        studyLogRepository.delete(studyLog);
    }

    @Override
    public StudyLog findByIdAndMemberId(Long id, String memberId) {
        return studyLogRepository.findByIdAndMemberEmail(id, memberId).orElseThrow(() -> new NotFoundException(NOT_FOUND_STUDY_LOG));
    }

    @Override
    public List<StudyLogResponse> findAllStudyLogByYearAndMonthAndMemberId(Integer year, Integer month, String memberId) {
        BooleanBuilder builder = new BooleanBuilder();

        if (year != null) {
            builder.and(studyLog.studyDate.year().eq(year));
        }

        if (month != null) {
            builder.and(studyLog.studyDate.month().eq(month));
        }

        builder.and(studyLog.member.email.eq(memberId));

        return jpaQueryFactory
                .select(Projections.constructor(StudyLogResponse.class,
                        studyLog.id,
                        studyLog.studyDate,
                        studyLog.totalStudyTimes,
                        studyLog.content,
                        studyLog.inputSource))
                .from(studyLog)
                .where(builder)
                .fetch();

    }
}
