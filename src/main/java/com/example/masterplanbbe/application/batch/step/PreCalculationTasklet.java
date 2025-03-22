package com.example.masterplanbbe.application.batch.step;

import com.example.masterplanbbe.infrastructure.repository.BatchIntermediateStepJdbcRepository;
import com.example.masterplanbbe.infrastructure.repository.BatchRecommendationJdbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PreCalculationTasklet implements Tasklet {

    private final BatchIntermediateStepJdbcRepository batchIntermediateStepJdbcRepository;
    private final BatchRecommendationJdbcRepository batchRecommendationJdbcRepository;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
        batchIntermediateStepJdbcRepository.deleteAll();
        batchRecommendationJdbcRepository.deleteAll();
        return RepeatStatus.FINISHED;
    }
}
