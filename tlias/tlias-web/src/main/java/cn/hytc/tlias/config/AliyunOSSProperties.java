package cn.hytc.tlias.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 阿里云OSS配置属性
 */
@Data
@Component
@ConfigurationProperties(prefix = "aliyun.oss")
public class AliyunOSSProperties {
    private String endpoint;    // OSS访问域名
    private String bucketName;  // 存储空间名称
    private String region;      // 地域

    @PostConstruct
    public void init() {
        System.out.println("endpoint = " + endpoint);
        System.out.println("bucketName = " + bucketName);
        System.out.println("region = " + region);
    }
}