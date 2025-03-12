package com.example.masterplanbbe.domain.chat.util;

/**
 * Twitter Snowflake 기반의 ID 생성기
 * 64비트 ID를 생성하며, 시간순 정렬이 보장됨.
 */
public class SnowflakeIdGenerator {

    // 기준 시간 (2025-01-01 00:00:00 UTC)
    private final long epoch = 1735689600000L;
    private final long workerId;
    private long sequence = 0L;
    private long lastTimestamp = -1L;

    public SnowflakeIdGenerator(long workerId) {
        this.workerId = workerId;
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
