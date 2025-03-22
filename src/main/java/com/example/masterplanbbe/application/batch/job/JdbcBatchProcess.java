package com.example.masterplanbbe.application.batch.job;

import com.example.masterplanbbe.application.batch.dto.*;
import com.example.masterplanbbe.application.batch.step.*;
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

    private final PreFirstTasklet preFirstTasklet;

    private final FirstStepReader firstStepReader;
    private final FirstStepProcess firstStepProcess;
    private final FirstStepWriter firstStepWriter;

    private final SecondStepReader secondStepReader;
    private final SecondStepProcess secondStepProcess;
    private final SecondStepWriter secondStepWriter;

    private final ThirdStepReader thirdStepReader;
    private final ThirdStepProcess thirdStepProcess;
    private final ThirdStepWriter thirdStepWriter;

    @Bean
    public Job job() {
        log.info("추천 점수 연산 배치 처리 작업 실시");

        return new JobBuilder("jdbcBatchJob", jobRepository)
                .start(preStep())
                .next(fistStep())
                .next(secondStep())
                .next(thirdStep())
                .build();
    }

    @Bean
    public Step preStep() {
        log.info("Pre Step : 모든 중간 연산 및 최종 연산 테이블 비우기");

        return new StepBuilder("preStep", jobRepository)
                .tasklet(preFirstTasklet, transactionManager)
                .build();
    }

    @Bean
    public Step fistStep() {
        log.info("Step 1 : 연령대 무관 그룹 불필요 필드 기반 추천점수 중간 연산");

        return new StepBuilder("firstStep", jobRepository)
                .<FirstStepReadDTO, NonAgeCalculationWriteDTO>chunk(10, transactionManager)
                .reader(firstStepReader)
                .processor(firstStepProcess)
                .writer(firstStepWriter)
                .build();
    }

    @Bean
    public Step secondStep() {
        log.info("Step 2 : 연령대 무관 그룹 합산 필드 기반 추천점수 중간 연산");

        return new StepBuilder("secondStep", jobRepository)
                .<SecondStepReadDTO, NonAgeCalculationWriteDTO>chunk(10, transactionManager)
                .reader(secondStepReader)
                .processor(secondStepProcess)
                .writer(secondStepWriter)
                .build();
    }

    @Bean
    public Step thirdStep() {
        log.info("Step 3 : 연령대 기반 그룹 합산 필드 기반 추천점수 최종 연산");

        return new StepBuilder("thirdStep", jobRepository)
                .<ThirdStepReadDTO, UseAgeCalculationWriteDTO>chunk(10, transactionManager)
                .reader(thirdStepReader)
                .processor(thirdStepProcess)
                .writer(thirdStepWriter)
                .build();
    }
}
