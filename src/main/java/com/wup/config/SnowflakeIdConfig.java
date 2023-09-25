package com.wup.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "snow-flake")
public class SnowflakeIdConfig {
    // 起始时间戳，2021-09-24 00:00:00 UTC
    private long START_TIMESTAMP;
    // Worker ID位数
    private long WORKER_ID_BITS;
    // Data Center ID位数
    private long DATA_CENTER_ID_BITS;
    // 最大支持的Worker ID，对应位数的最大值
    private long MAX_WORKER_ID;
    // 最大支持的Data Center ID，对应位数的最大值
    private long MAX_DATA_CENTER_ID;
    // 序列号位数
    private long SEQUENCE_BITS;

    // Worker ID左移位数
    private long WORKER_ID_SHIFT;
    // Data Center ID左移位数
    private long DATA_CENTER_ID_SHIFT;
    // 时间戳左移位数
    private long TIMESTAMP_LEFT_SHIFT;
    // 序列号的掩码，用于保证序列号不会超出位数的范围
    private long SEQUENCE_MASK;

    // Worker ID 和 Data Center ID
    private long workerId;
    private long dataCenterId;
    private long lastTimestamp;
    private long sequence;
}
