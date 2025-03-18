package com.example.masterplanbbe.application.batch.step;

import com.example.masterplanbbe.infrastructure.repository.BatchFirstStepJdbcRepository;
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

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
        batchFirstStepJdbcRepository.deleteAll();
        return RepeatStatus.FINISHED;
    }
}
