package com.example.masterplanbbe.infrastructure.util;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class SnowflakeIdGenerator {
    private final long epoch = 1735689600000L;
    private final long workerId; //이후 서버 확장시 환경변수로 치환
    private long sequence = 0L;
    private long lastTimestamp = -1L;

    public SnowflakeIdGenerator() {
        this.workerId = Math.abs(UUID.randomUUID().getLeastSignificantBits() % 32);
    }

    public synchronized long nextId() {
        long timestamp = System.currentTimeMillis();

        if (timestamp < lastTimestamp) {
            throw new RuntimeException();
        }

        if (timestamp == lastTimestamp) {
            sequence = (sequence + 1) & 4095;

            if (sequence == 0) {
                while (timestamp <= lastTimestamp) {
                    timestamp = System.currentTimeMillis();
                }
            }
        } else {
            sequence = 0L;
        }

        lastTimestamp = timestamp;
        return ((timestamp - epoch) << 22) | (workerId << 12) | sequence;
    }
}
