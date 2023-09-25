package com.wup.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.wup.config.AliOOSProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * 阿里云 OSS 工具类
 */

@Component
public class AliOSSUtils {
    @Autowired
    private AliOOSProperties aliOOSProperties;

    /**
     * 实现上传图片到OSS
     */
    public String upload(MultipartFile file) throws IOException {
        // 获取上传的文件的输入流
        InputStream inputStream = file.getInputStream();

        // 避免文件覆盖
        String originalFilename = file.getOriginalFilename();
        String fileName = UUID.randomUUID().toString() + originalFilename.substring(originalFilename.lastIndexOf("."));

        //上传文件到 OSS
        OSS ossClient = new OSSClientBuilder().build(aliOOSProperties.getEndpoint(), aliOOSProperties.getAccessKeyId(), aliOOSProperties.getAccessKeySecret());
        ossClient.putObject(aliOOSProperties.getBucketName(), fileName, inputStream);

        //文件访问路径
        String url = aliOOSProperties.getEndpoint().split("//")[0] + "//" + aliOOSProperties.getBucketName() + "." + aliOOSProperties.getEndpoint().split("//")[1] + "/" + fileName;
        // 关闭ossClient
        ossClient.shutdown();
        return url;// 把上传到oss的路径返回
    }


}