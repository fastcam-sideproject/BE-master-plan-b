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

    private final PreCalculationTasklet preCalculationTasklet;

    // 여기가 최종 확정
    private final NonAgeCalculationReader nonAgeCalculationReader;
    private final NonAgeCalculationProcess nonAgeCalculationProcess;
    private final NonAgeCalculationWriter nonAgeCalculationWriter;

    private final UseAgeCalculationReader useAgeCalculationReader;
    private final UseAgeCalculationProcess useAgeCalculationProcess;
    private final UseAgeCalculationWriter useAgeCalculationWriter;

    private final FinalCalculationReader finalCalculationReader;
    private final FinalCalculationProcess finalCalculationProcess;
    private final FinalCalculationWriter finalCalculationWriter;

    private final SortingRankTasklet sortingRankTasklet;

    @Bean
    public Job job() {
        log.info("추천 점수 연산 배치 처리 작업 실시");

        return new JobBuilder("jdbcBatchJob", jobRepository)
                .start(preCalculation())
                .next(nonAgeCalculationStep())
                .next(UseAgeCalculationStep())
                .next(finalCalculationStep())
                .next(sortRanking())
                .build();
    }

    @Bean
    public Step preCalculation() {
        log.info("Pre Step : 모든 중간 연산 및 최종 연산 테이블 비우기");

        return new StepBuilder("preCalculation", jobRepository)
                .tasklet(preCalculationTasklet, transactionManager)
                .build();
    }

    @Bean
    public Step nonAgeCalculationStep() {
        log.info("Step 1 : 연령대 무관 추천점수 중간 연산");

        return new StepBuilder("nonAgeCalculation", jobRepository)
                .<PreCalculationDTO, NonAgeCalculationWriteDTO>chunk(10, transactionManager)
                .reader(nonAgeCalculationReader)
                .processor(nonAgeCalculationProcess)
                .writer(nonAgeCalculationWriter)
                .build();
    }

    @Bean
    public Step UseAgeCalculationStep() {
        log.info("Step 2 : 연령대 기반 그룹 합산 필드 기반 추천점수 최종 연산");

        return new StepBuilder("useAgeCalculation", jobRepository)
                .<GroupAgeReadDTO, UseAgeCalculationWriteDTO>chunk(10, transactionManager)
                .reader(useAgeCalculationReader)
                .processor(useAgeCalculationProcess)
                .writer(useAgeCalculationWriter)
                .build();
    }

    // 직업별 상위 4개 사전 추출 스텝 추가...?
    // Redis에 넣기?
    // Redis -> 캐싱, 일단 SQL에서 최대한 짜내보기
    @Bean
    public Step finalCalculationStep() {
        log.info("Step 3 : 스펙별 직무 및 카테고리 조인 처리");

        return new StepBuilder("finalCalculation", jobRepository)
                .<FinalJoinReadDTO, FinalCalculationWriteDTO>chunk(10, transactionManager)
                .reader(finalCalculationReader)
                .processor(finalCalculationProcess)
                .writer(finalCalculationWriter)
                .build();
    }

    @Bean
    public Step sortRanking() {
        log.info("Step 4 : 최종 추천점수 기반 랭킹 정렬");

        return new StepBuilder("sortRanking", jobRepository)
                .tasklet(sortingRankTasklet, transactionManager)
                .build();
    }
}
