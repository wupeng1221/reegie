package com.wup;

import com.aliyun.oss.OSS;
import com.aliyuncs.auth.AlibabaCloudCredentials;
import com.wup.config.AliOOSProperties;
import com.wup.utils.SnowflakeIdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import javax.management.openmbean.OpenMBeanOperationInfoSupport;

@Slf4j
@SpringBootTest
class ReegieApplicationTests {
    @Autowired
    private SnowflakeIdGenerator snowflakeIdGenerator;
    @Autowired
    private AliOOSProperties aliOOSProperties;
    //private OSS ossClient = aliOOSProperties.oosClient();

    @Test
    void testDownloadFromAliyunOOS(){
        log.info(aliOOSProperties.toString());
        String fileURL = "https://wupeng-javaweb.oss-cn-chengdu.aliyuncs.com/5560dba6-7e12-4f8c-b91a-11a3c1360989.png";
        String originalName = fileURL.substring(aliOOSProperties.getBucketName().length() + aliOOSProperties.getEndpoint().length() + 2);
        log.info(fileURL);
        log.info(originalName);
    }

}
