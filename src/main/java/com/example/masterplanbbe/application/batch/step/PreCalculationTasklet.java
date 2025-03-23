package com.example.masterplanbbe.application.batch.step;

import com.example.masterplanbbe.infrastructure.repository.BatchNonAgeCalculationJdbcRepository;
import com.example.masterplanbbe.infrastructure.repository.BatchUseAgeCalculationJdbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PreCalculationTasklet implements Tasklet {

    private final BatchNonAgeCalculationJdbcRepository batchNonAgeCalculationJdbcRepository;
    private final BatchUseAgeCalculationJdbcRepository batchUseAgeCalculationJdbcRepository;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
        batchNonAgeCalculationJdbcRepository.deleteAll();
        batchUseAgeCalculationJdbcRepository.deleteAll();
        return RepeatStatus.FINISHED;
    }
}
