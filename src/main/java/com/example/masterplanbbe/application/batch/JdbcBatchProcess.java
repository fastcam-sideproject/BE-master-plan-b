package com.example.masterplanbbe.application.batch;

import com.example.masterplanbbe.domain.entity.Recommendation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class JdbcBatchProcess {

    private final JobRepository jobRepository;
    private final DataSource dataSource;
    private final JdbcTemplate jdbcTemplate;
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
//                .<Recommendation, Recommendation>chunk(10, transactionManager)
//                .writer(updateOldScoreWriter())
                .tasklet(updateOldScoreTasklet(), transactionManager)
                .build();
    }

    /**
     * 기존의 신규 추천점수를 구 추천점수로 업데이트하는 Writer
     */
    @Bean
    public Tasklet updateOldScoreTasklet() {
        return (contribution, chunkContext) -> {
            // SQL 쿼리로 기존의 신규 추천점수를 구 추천점수로 업데이트
            int updatedRows = jdbcTemplate.update("UPDATE recommendations SET old_score = new_score");
            log.info("추천 점수 업데이트 완료, {}개의 레코드 업데이트", updatedRows);

            return RepeatStatus.FINISHED;  // 작업 완료 후 종료
        };
    }
}
