package com.example.masterplanbbe.domain.exam.service;

import com.example.masterplanbbe.domain.exam.request.ExamScheduleCreateRequest;
import com.example.masterplanbbe.domain.exam.response.CreateExamScheduleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ExamScheduleService {

    public CreateExamScheduleResponse create(ExamScheduleCreateRequest request) {
        // TODO : 시험 정보 조회

        // TODO : 시험 일정 등록

        return CreateExamScheduleResponse.of(1L, 1L, "location", LocalDate.now(), "sessionType");
    }
}
