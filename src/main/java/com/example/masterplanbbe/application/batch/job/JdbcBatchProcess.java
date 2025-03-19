package com.example.masterplanbbe.application.batch.job;

import com.example.masterplanbbe.application.batch.dto.FirstStepReadDTO;
import com.example.masterplanbbe.application.batch.dto.IntermediateStepWriteDTO;
import com.example.masterplanbbe.application.batch.step.FirstStepProcess;
import com.example.masterplanbbe.application.batch.step.FirstStepReader;
import com.example.masterplanbbe.application.batch.step.FirstStepWriter;
import com.example.masterplanbbe.application.batch.step.InitTasklet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class JdbcBatchProcess {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    private final InitTasklet initTasklet;

    private final FirstStepReader firstStepReader;
    private final FirstStepProcess firstStepProcess;
    private final FirstStepWriter firstStepWriter;

    @Bean
    public Job job() {
        log.info("추천 점수 연산 배치 처리 작업 실시");

        return new JobBuilder("jdbcBatchJob", jobRepository)
                .start(initStep())
                .next(fistStep())
                .build();
    }

    @Bean
    public Step initStep() {
        log.info("Step 0 : 모든 중간 연산 테이블 삭제");

        return new StepBuilder("initStep", jobRepository)
                .tasklet(initTasklet, transactionManager)
                .build();
    }

    @Bean
    public Step fistStep() {
        log.info("Step 1 : 연령대 무관 그룹 불필요 필드 기반 추천점수 중간 연산");

        return new StepBuilder("firstStep", jobRepository)
                .<FirstStepReadDTO, IntermediateStepWriteDTO>chunk(10, transactionManager)
                .reader(firstStepReader)
                .processor(firstStepProcess)
                .writer(firstStepWriter)
                .build();
    }

    @Bean
    public Step secondStep() {
        log.info("Step 2 : 연령대 무관 그룹 합산 필드 기반 추천점수 중간 연산");

        return null;
    }
}
