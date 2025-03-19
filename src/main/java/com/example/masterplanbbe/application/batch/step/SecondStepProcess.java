package com.example.masterplanbbe.application.batch.step;

import com.example.masterplanbbe.application.batch.dto.IntermediateStepWriteDTO;
import com.example.masterplanbbe.application.batch.dto.SecondStepReadDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SecondStepProcess implements ItemProcessor<SecondStepReadDTO, IntermediateStepWriteDTO> {

    private static final double LIKE_COEFFICIENT = 0.1;
    private static final double VIEW_COEFFICIENT = 0.1;

    @Override
    public IntermediateStepWriteDTO process(SecondStepReadDTO item) {
        double totalLikesPoint = (double) item.totalLikeCount();
        double totalViewPoint = (double) item.totalViewCount();

        Double intermediateResult = totalLikesPoint * LIKE_COEFFICIENT
                + totalViewPoint * VIEW_COEFFICIENT;

        return new IntermediateStepWriteDTO(item.specId(), item.examId(), intermediateResult);
    }
}
