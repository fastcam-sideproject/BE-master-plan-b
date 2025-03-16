package com.example.masterplanbbe.application.batch;

import com.example.masterplanbbe.domain.entity.Recommendation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class JdbcBatchProcess {

    private final JobRepository jobRepository;
    private final DataSource dataSource;
    private final PlatformTransactionManager transactionManager;

    @Bean
    public Job job() {
        log.info("추천 점수 연산 배치 처리 작업 실시");

        return new JobBuilder("jdbcBatchJob", jobRepository)
                .start(firstStep())
                .build();
    }

    @Bean
    public Step firstStep() {
        log.info("Step 1: 기존 추천점수 구 추천점수 필드로 업데이트");

        return new StepBuilder("firstStep", jobRepository)
                .<Recommendation, Recommendation>chunk(10, transactionManager)
                .writer(updateOldScoreWriter())
                .build();
    }

    /**
     * 기존의 신규 추천점수를 구 추천점수로 업데이트하는 Writer
     */
    @Bean
    public JdbcBatchItemWriter<Recommendation> updateOldScoreWriter() {
        return new JdbcBatchItemWriterBuilder<Recommendation>()
                .dataSource(dataSource)
                .sql("UPDATE recommendations SET old_score = :newScore WHERE id = :id")
                .beanMapped() // Java 객체 필드를 SQL 파라미터로 자동 매핑
                .build();
    }
}
