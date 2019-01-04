package cn.doublehh.common.constant;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author 胡昊
 * Description: COS配置信息
 * Date: 2019/1/3
 * Time: 22:00
 * Create: DoubleH
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "cos")
public class CosClientConstant {
    private String secretId;
    private String secretKey;
    private String bucket;
    private String bucketName;
    private String resourcesUrl;
}
