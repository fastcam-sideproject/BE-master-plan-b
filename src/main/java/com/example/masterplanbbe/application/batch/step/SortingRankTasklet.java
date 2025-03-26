package com.example.masterplanbbe.application.batch.step;

import com.example.masterplanbbe.infrastructure.repository.BatchSortingRankJdbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SortingRankTasklet implements Tasklet {

    private final BatchSortingRankJdbcRepository batchSortingRankJdbcRepository;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
        batchSortingRankJdbcRepository.insert();
        return RepeatStatus.FINISHED;
    }
}
