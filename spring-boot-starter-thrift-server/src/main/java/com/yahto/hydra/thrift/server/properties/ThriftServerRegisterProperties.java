package com.yahto.hydra.thrift.server.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author yahto
 * @date 2019/12/10 7:57 PM
 */
@Data
@ConfigurationProperties(prefix = "spring.thrift.server.register")
public class ThriftServerRegisterProperties {
    /**
     * 是否允许服务注册
     */
    private Boolean enabled = false;

    /**
     * zookeeper的地址
     */
    private String zkHost;
}
