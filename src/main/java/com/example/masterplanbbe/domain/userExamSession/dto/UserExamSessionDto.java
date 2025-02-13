package com.example.masterplanbbe.domain.userExamSession.dto;

import com.example.masterplanbbe.domain.exam.entity.Exam;
import com.example.masterplanbbe.member.entity.Member;

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
