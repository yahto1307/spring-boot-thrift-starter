package com.yahto.hydra.thrift.server.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "spring.thrift.server.hs-ha")
public class THsHaServerProperties {

    /**
     * 最少工作线程数量
     */
    private int minWorkerThreads = 5;

    /**
     * 最多工作线程数量
     */
    private int maxWorkerThreads = 20;

    /**
     * 线程的存活时间 (min)
     */
    private int keepAliveTime = 1;
}
