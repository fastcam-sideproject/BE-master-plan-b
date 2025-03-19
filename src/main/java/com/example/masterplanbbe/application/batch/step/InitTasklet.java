package com.example.masterplanbbe.application.batch.step;

import com.example.masterplanbbe.infrastructure.repository.BatchFirstStepJdbcRepository;
import com.example.masterplanbbe.infrastructure.repository.BatchSecondStepJdbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InitTasklet implements Tasklet {

    private final BatchFirstStepJdbcRepository batchFirstStepJdbcRepository;
    private final BatchSecondStepJdbcRepository batchSecondStepJdbcRepository;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
        batchFirstStepJdbcRepository.deleteAll();
        batchSecondStepJdbcRepository.deleteAll();
        return RepeatStatus.FINISHED;
    }
}
