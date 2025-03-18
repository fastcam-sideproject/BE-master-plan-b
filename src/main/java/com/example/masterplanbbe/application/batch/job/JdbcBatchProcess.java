package com.example.masterplanbbe.application.batch.job;

import com.example.masterplanbbe.application.batch.dto.FirstStepReadDTO;
import com.example.masterplanbbe.application.batch.dto.FirstStepWriteDTO;
import com.example.masterplanbbe.application.batch.step.FirstStepProcess;
import com.example.masterplanbbe.application.batch.step.FirstStepReader;
import com.example.masterplanbbe.application.batch.step.FirstStepWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
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
    private final PlatformTransactionManager transactionManager;

    private final FirstStepReader firstStepReader;
    private final FirstStepProcess firstStepProcess;
    private final FirstStepWriter firstStepWriter;

    @Bean
    public Job job() {
        log.info("추천 점수 연산 배치 처리 작업 실시");

        return new JobBuilder("jdbcBatchJob", jobRepository)
                .start(firstStep())
                .build();
    }

    @Bean
    public Step firstStep() {
        log.info("Step 1 : 연령대 무관 필드 기반 추천점수 파라미터 연산");

        return new StepBuilder("firstStep", jobRepository)
                .<FirstStepReadDTO, FirstStepWriteDTO>chunk(10, transactionManager)
                .reader(firstStepReader)
                .processor(firstStepProcess)
                .writer(firstStepWriter)
                .build();
    }

//    @Bean
//    public Step firstStep() {
//        log.info("Step 1: 기존 추천점수 구 추천점수 필드로 업데이트");
//
//        return new StepBuilder("firstStep", jobRepository)
////                .<Recommendation, Recommendation>chunk(10, transactionManager)
////                .writer(updateOldScoreWriter())
//                .tasklet(updateOldScoreTasklet(), transactionManager)
//                .build();
//    }
//
//    /**
//     * 기존의 신규 추천점수를 구 추천점수로 업데이트하는 TaskLet(Reader, Process, Writer 필요 없는 작업)
//     */
//    @Bean
//    public Tasklet updateOldScoreTasklet() {
//        return (contribution, chunkContext) -> {
//            // SQL 쿼리로 기존의 신규 추천점수를 구 추천점수로 업데이트
//            int updatedRows = jdbcTemplate.update("UPDATE recommendations SET old_score = new_score");
//            log.info("추천 점수 업데이트 완료, {}개의 레코드 업데이트", updatedRows);
//
//            return RepeatStatus.FINISHED;  // 작업 완료 후 종료
//        };
//    }
//
//    @Bean
//    public Step firstStep() {
//        log.info("Step 1: 기존 추천점수 구 추천점수 필드로 업데이트");
//
//        return new StepBuilder("firstStep", jobRepository)
//                .<Recommendation, Recommendation>chunk(10, transactionManager)
//                .writer(updateOldScoreWriter())
//                .build();
//    }
//
//    /**
//     * 기존의 신규 추천점수를 구 추천점수로 업데이트하는 Writer
//     */
//    @Bean
//    public JdbcBatchItemWriter<Recommendation> updateOldScoreWriter() {
//        return new JdbcBatchItemWriterBuilder<Recommendation>()
//                .dataSource(dataSource)
//                .sql("UPDATE recommendations SET old_score = :newScore WHERE id = :id")
//                .beanMapped() // Java 객체 필드를 SQL 파라미터로 자동 매핑
//                .build();
//    }
}
