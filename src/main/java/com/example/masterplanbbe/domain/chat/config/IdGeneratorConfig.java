package com.example.masterplanbbe.domain.chat.config;

import com.example.masterplanbbe.domain.chat.util.SnowflakeIdGenerator;
import com.example.masterplanbbe.domain.chat.util.WorkerIdAllocator;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class IdGeneratorConfig {
    private final WorkerIdAllocator workerIdAllocator;

    @Bean
    public SnowflakeIdGenerator snowflakeIdGenerator() {
        long workerId = workerIdAllocator.getWorkerId();
        return new SnowflakeIdGenerator(workerId);
    }
}