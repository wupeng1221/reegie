package com.wup.utils;

import com.wup.config.SnowflakeIdConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SnowflakeIdGenerator {
    @Autowired
    private SnowflakeIdConfig snowflakeIdConfig;
    public synchronized long generateId() {
        long timestamp = System.currentTimeMillis();

        if (timestamp < snowflakeIdConfig.getLastTimestamp()) {
            throw new RuntimeException("Clock moved backwards. Refusing to generate id");
        }

        if (timestamp == snowflakeIdConfig.getLastTimestamp()) {
            snowflakeIdConfig.setSequence((snowflakeIdConfig.getSequence() + 1) & snowflakeIdConfig.getSEQUENCE_MASK());
            if (snowflakeIdConfig.getSequence() == 0) {
                timestamp = tilNextMillis(snowflakeIdConfig.getLastTimestamp());
            }
        } else {
            snowflakeIdConfig.setSequence(0L);
        }

        snowflakeIdConfig.setLastTimestamp(timestamp);

        return ((timestamp - snowflakeIdConfig.getSTART_TIMESTAMP()) << snowflakeIdConfig.getTIMESTAMP_LEFT_SHIFT()) |
               (snowflakeIdConfig.getDataCenterId() << snowflakeIdConfig.getDATA_CENTER_ID_SHIFT()) |
               (snowflakeIdConfig.getWorkerId() << snowflakeIdConfig.getWORKER_ID_SHIFT()) |
               snowflakeIdConfig.getSequence();
    }

    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = System.currentTimeMillis();
        while (timestamp <= lastTimestamp) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }
}
