//package com.example.masterplanbbe.presentation.controller;
//
//import io.swagger.v3.oas.annotations.Hidden;
//import io.swagger.v3.oas.annotations.Operation;
//import lombok.RequiredArgsConstructor;
//import org.springframework.batch.core.JobParameters;
//import org.springframework.batch.core.JobParametersBuilder;
//import org.springframework.batch.core.configuration.JobRegistry;
//import org.springframework.batch.core.launch.JobLauncher;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.UUID;
//
//@Hidden
//@RestController
//@RequestMapping("/batch")
//@RequiredArgsConstructor
//public class BatchController {
//
//    private final JobLauncher jobLauncher;
//    private final JobRegistry jobRegistry;
//
//    @Operation(hidden = true)
//    @GetMapping
//    public String test() throws Exception {
//        JobParameters jobParameters = new JobParametersBuilder()
//                .addString("param", UUID.randomUUID().toString())
//                .toJobParameters();
//
//        jobLauncher.run(jobRegistry.getJob("jdbcBatchJob"), jobParameters);
//        return "배치 테스트 확인";
//    }
//}
