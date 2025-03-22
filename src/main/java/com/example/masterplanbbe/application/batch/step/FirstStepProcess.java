package com.example.masterplanbbe.application.batch.step;

import com.example.masterplanbbe.application.batch.dto.FirstStepReadDTO;
import com.example.masterplanbbe.application.batch.dto.NonAgeCalculationWriteDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Slf4j
@Component
public class FirstStepProcess implements ItemProcessor<FirstStepReadDTO, NonAgeCalculationWriteDTO> {

    private static final double APPLY_COEFFICIENT = 0.2;
    private static final double EXAM_START_COEFFICIENT = 0.01;
    private static final double PARTICIPANT_COEFFICIENT = 0.3;

    @Override
    public NonAgeCalculationWriteDTO process(FirstStepReadDTO item) {
        LocalDate today = LocalDate.now();

        // 날짜가 가까울수록 높은 점수를 부여하는 역수 방식
        double applyEndPoint = 1.0 / (1 + ChronoUnit.DAYS.between(today, item.applyEndDate()));
        double examStartPoint = 1.0 / (1 + ChronoUnit.DAYS.between(today, item.examStartDate()));

        // 참여자 수를 반영하되, 로그 스케일로 증가폭 조절
        double participantPoint = Math.log(1 + item.participantCount());

        // 가중치 조정
        Double intermediateResult =
                applyEndPoint * 0.5    // 지원 마감일 가중치 증가
                        + examStartPoint * 0.3   // 시험 시작일 가중치
                        + participantPoint * 0.2; // 참여자 가중치

        return new NonAgeCalculationWriteDTO(item.specId(), item.examId(), intermediateResult);
    }
}
