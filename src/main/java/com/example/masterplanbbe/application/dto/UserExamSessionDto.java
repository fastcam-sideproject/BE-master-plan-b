package com.example.masterplanbbe.application.dto;

import com.example.masterplanbbe.domain.entity.Exam;
import com.example.masterplanbbe.domain.entity.Member;

import java.time.LocalDate;
import java.time.LocalTime;

public record UserExamSessionDto(
        Long id,
        Exam exam,
        Member member,
        LocalDate date,
        LocalTime startTime,
        LocalTime endTime

) {
}
