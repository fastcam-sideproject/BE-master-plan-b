package com.example.masterplanbbe.application.batch.step;

import com.example.masterplanbbe.application.batch.dto.FirstStepReadDTO;
import com.example.masterplanbbe.application.batch.dto.FirstStepWriteDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Slf4j
@Component
public class FirstStepProcess implements ItemProcessor<FirstStepReadDTO, FirstStepWriteDTO> {

    private static final double APPLY_COEFFICIENT = 0.2;
    private static final double EXAM_COEFFICIENT = 0.01;
    private static final double PARTICIPANT_COEFFICIENT = 0.3;

    @Override
    public FirstStepWriteDTO process(FirstStepReadDTO item) {
//        log.info("First Process 데이터 : {}", item);
        LocalDate today = LocalDate.now();

        double applyEndPoint = (double) Math.max(0, ChronoUnit.DAYS.between(today, item.applyEndDate()));
        double examStartPoint = (double) Math.max(0, ChronoUnit.DAYS.between(today, item.examStartDate()));
        double participantPoint = (double) item.participantCount();

        Double intermediateResult = applyEndPoint * APPLY_COEFFICIENT +
                        examStartPoint * EXAM_COEFFICIENT +
                        participantPoint * PARTICIPANT_COEFFICIENT;

        return new FirstStepWriteDTO(item.examId(), intermediateResult);
    }
}
